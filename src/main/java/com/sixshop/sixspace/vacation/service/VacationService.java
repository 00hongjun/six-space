package com.sixshop.sixspace.vacation.service;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.util.stream.Collectors.groupingBy;

import com.sixshop.sixspace.user.domain.User;
import com.sixshop.sixspace.user.infrastructure.UserRepository;
import com.sixshop.sixspace.vacation.domain.DayOfMonthVacation;
import com.sixshop.sixspace.vacation.domain.Vacation;
import com.sixshop.sixspace.vacation.repository.VacationRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacationService {

    private static final int LUNCH_HOUR = 13;
    private static final int FINISH_WORK_HOUR = 18;

    private final VacationRepository vacationRepository;
    private final UserRepository userRepository;

    public List<DayOfMonthVacation> getVacationsOfMonth(final int year, final int month) {
        final List<Vacation> vacations = findAllByBetweenDates(year, month);
        final List<DayOfMonthVacation> filtered = filterVacationOfMonth(year, month, vacations);
        final List<DayOfMonthVacation> vacationsOfMonth = distinctDayVacation(filtered);
        final List<String> userIds = vacationsOfMonth.stream()
                                                     .map(DayOfMonthVacation::getUserId)
                                                     .collect(Collectors.toList());
        final List<User> users = userRepository.findUserByIds(userIds);

        for (User user : users) {
            vacationsOfMonth.forEach(
                filteredVacation -> {
                    if (filteredVacation.isMine(user.getId())) {
                        filteredVacation.setUserName(user.getName());
                    }
                }
            );
        }
        return vacationsOfMonth;
    }

    @Transactional(readOnly = true)
    public List<Vacation> findAllByBetweenDates(final int year, final int month) {
        final LocalDateTime startDayOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        final LocalDateTime endDayOfMonth = startDayOfMonth.with(lastDayOfMonth())
                                                           .withHour(23)
                                                           .withMinute(59);

        return vacationRepository.findAllByBetweenDates(startDayOfMonth, endDayOfMonth);
    }

    private List<DayOfMonthVacation> filterVacationOfMonth(final int year, final int month,
        final List<Vacation> vacations) {
        final LocalDate startDayOfMonth = LocalDate.of(year, month, 1);
        final LocalDate endDayOfMonth = startDayOfMonth.with(lastDayOfMonth());
        final List<DayOfMonthVacation> filtered = new ArrayList<>();

        for (final Vacation vacation : vacations) {
            final LocalDate from = vacation.getStartDateTime().toLocalDate();
            final LocalDate to = vacation.getEndDateTime().toLocalDate();

            int totalUseHour = vacation.getUseHour();
            int startHour = vacation.getStartDateTime().getHour();

            LocalDate cursor = from;
            while (!cursor.isAfter(to)) {
                int use = getUseHour(startHour, totalUseHour);
                if (isInMonth(cursor, startDayOfMonth, endDayOfMonth)) {
                    filtered.add(new DayOfMonthVacation(vacation.getUserId(), cursor.getDayOfMonth(), use));
                }

                cursor = cursor.plusDays(1);
                totalUseHour -= use;
                startHour = 9;
            }

            if (totalUseHour != 0) {
                log.warn("hour calculate error : {}", vacation);
                throw new RuntimeException("일 별 휴가를 계산할 수 없습니다. 관리자에게 문의하세요");
            }
        }
        return filtered;
    }

    private int getUseHour(final int startHour, final int totalUseHour) {
        if (startHour + totalUseHour <= FINISH_WORK_HOUR) {
            return totalUseHour;
        }

        int useHour = FINISH_WORK_HOUR - startHour;
        if (startHour < LUNCH_HOUR) {
            return useHour - 1;
        }
        return useHour;
    }

    private boolean isInMonth(final LocalDate currentDate, final LocalDate startDayOfMonth, final LocalDate endDayOfMonth) {
        return !currentDate.isBefore(startDayOfMonth) && !currentDate.isAfter(endDayOfMonth);
    }

    private List<DayOfMonthVacation> distinctDayVacation(final List<DayOfMonthVacation> filtered) {
        final Map<Integer, Map<String, List<DayOfMonthVacation>>> dayGroups = filtered.stream()
                                                                                      .collect(groupingBy(DayOfMonthVacation::getDay
                                                                                          , groupingBy(DayOfMonthVacation::getUserId)
                                                                                      ));

        List<DayOfMonthVacation> result = new ArrayList<>();
        for (Map.Entry<Integer, Map<String, List<DayOfMonthVacation>>> dayEntry : dayGroups.entrySet()) {
            for (Map.Entry<String, List<DayOfMonthVacation>> userEntry : dayEntry.getValue().entrySet()) {
                int todayUseHour = userEntry.getValue()
                                        .stream()
                                        .mapToInt(DayOfMonthVacation::getUseHour)
                                        .reduce(Integer::sum)
                                        .orElseThrow(() -> new IllegalArgumentException("사용한 휴가 시간을 계산할 수 없습니다. 관리자에게 문의하세요"));

                result.add(new DayOfMonthVacation(userEntry.getKey(), dayEntry.getKey(), todayUseHour));
            }
        }
        return result;
    }
}
