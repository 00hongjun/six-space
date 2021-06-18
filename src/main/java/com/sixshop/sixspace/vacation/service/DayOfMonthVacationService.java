package com.sixshop.sixspace.vacation.service;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.util.stream.Collectors.groupingBy;

import com.sixshop.sixspace.user.domain.User;
import com.sixshop.sixspace.user.infrastructure.UserRepository;
import com.sixshop.sixspace.vacation.domain.Vacation;
import com.sixshop.sixspace.vacation.domain.VacationLocalDateTime;
import com.sixshop.sixspace.vacation.presentation.dto.DayOfMonthVacation;
import com.sixshop.sixspace.vacation.presentation.dto.DayOfMonthVacationResponse;
import com.sixshop.sixspace.vacation.repository.DayOfMonthVacationRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
public class DayOfMonthVacationService {

    private static final int LUNCH_HOUR = 13;
    private static final int FINISH_WORK_HOUR = 18;

    private final DayOfMonthVacationRepository dayOfMonthVacationRepository;
    private final UserRepository userRepository;

    public DayOfMonthVacationResponse getVacationsOfMonth(final int year, final int month) {
        final List<Vacation> vacations = findAllByBetweenDates(year, month);
        final List<Vacation> perDayVacations = analyzePerDayVacation(year, month, vacations);
        final List<DayOfMonthVacation> vacationsOfMonth = distinctDayVacation(perDayVacations);
        setUserNames(vacationsOfMonth);
        return new DayOfMonthVacationResponse(vacationsOfMonth);
    }

    @Transactional(readOnly = true)
    public List<Vacation> findAllByBetweenDates(final int year, final int month) {
        final LocalDateTime startDayOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        final LocalDateTime endDayOfMonth = startDayOfMonth.with(lastDayOfMonth())
            .withHour(23)
            .withMinute(59);

        return dayOfMonthVacationRepository.findAllByBetweenDates(startDayOfMonth, endDayOfMonth);
    }

    private List<Vacation> analyzePerDayVacation(final int year, final int month, final List<Vacation> vacations) {
        final LocalDate startDayOfMonth = LocalDate.of(year, month, 1);
        final LocalDate endDayOfMonth = startDayOfMonth.with(lastDayOfMonth());
        final List<Vacation> analyzedVacations = new ArrayList<>();

        for (final Vacation vacation : vacations) {
            final LocalDate from = vacation.getStartDateTimeValue().toLocalDate();
            final LocalDate to = vacation.getEndDateTimeValue().toLocalDate();

            int totalUseHour = vacation.getUseHour();
            int startHour = vacation.getStartDateTimeValue().getHour();

            LocalDate cursor = from;
            while (!cursor.isAfter(to)) {
                int use = getUseHour(startHour, totalUseHour);
                if (isInMonth(cursor, startDayOfMonth, endDayOfMonth)) {
                    final VacationLocalDateTime dayStartTime = VacationLocalDateTime.of(cursor, LocalTime.of(startHour, 0));
                    final VacationLocalDateTime dayEndTime = VacationLocalDateTime.of(cursor, LocalTime.of(startHour, 0).plusHours(use));
                    analyzedVacations.add(new Vacation(vacation.getUserId(), dayStartTime, dayEndTime, use));
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
        return analyzedVacations;
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

    private boolean isInMonth(final LocalDate currentDate, final LocalDate startDayOfMonth,
        final LocalDate endDayOfMonth) {
        return !currentDate.isBefore(startDayOfMonth) && !currentDate.isAfter(endDayOfMonth);
    }

    private List<DayOfMonthVacation> distinctDayVacation(final List<Vacation> perDayVacations) {
        final Map<Integer, Map<String, List<Vacation>>> dayGroups = perDayVacations.stream()
                                                                                   .collect(groupingBy(vacation -> vacation.getStartDateTimeValue().getDayOfMonth()
                                                                                       , groupingBy(Vacation::getUserId))
                                                                                   );

        List<DayOfMonthVacation> result = new ArrayList<>();
        for (Map.Entry<Integer, Map<String, List<Vacation>>> dayEntry : dayGroups.entrySet()) {
            for (Map.Entry<String, List<Vacation>> userEntry : dayEntry.getValue().entrySet()) {
                int todayUseHour = userEntry.getValue()
                                            .stream()
                                            .mapToInt(Vacation::getUseHour)
                                            .reduce(Integer::sum)
                                            .orElseThrow(() -> new IllegalArgumentException("사용한 휴가 시간을 계산할 수 없습니다. 관리자에게 문의하세요"));

                result.add(new DayOfMonthVacation(userEntry.getKey(), dayEntry.getKey(), todayUseHour));
            }
        }
        return result;
    }

    private void setUserNames(final List<DayOfMonthVacation> vacationsOfMonth) {
        final List<String> userIds = vacationsOfMonth.stream()
                                                     .map(DayOfMonthVacation::getUserId)
                                                     .distinct()
                                                     .collect(Collectors.toList());

        final List<User> users = userRepository.findAllByIdIn(userIds);

        for (User user : users) {
            vacationsOfMonth.forEach(
                vacation -> {
                    if (vacation.isMine(user.getId())) {
                        vacation.setUserName(user.getName());
                    }
                }
            );
        }
    }
}