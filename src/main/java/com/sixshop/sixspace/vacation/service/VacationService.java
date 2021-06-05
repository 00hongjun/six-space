package com.sixshop.sixspace.vacation.service;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.util.stream.Collectors.groupingBy;

import com.sixshop.sixspace.vacation.domain.Vacation;
import com.sixshop.sixspace.vacation.repository.VacationRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public List<FilteredVacation> getVacationsOfMonth(final int year, final int month) {
        final List<Vacation> vacations = findAllByBetweenDates(year, month);
        final List<FilteredVacation> filtered = filterVacationOfMonth(year, month, vacations);
        return distinctDayVacation(filtered);
    }

    @Transactional(readOnly = true)
    public List<Vacation> findAllByBetweenDates(final int year, final int month) {
        final LocalDateTime startDayOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        final LocalDateTime endDayOfMonth = startDayOfMonth.with(lastDayOfMonth())
                                                           .withHour(23)
                                                           .withMinute(59);

        return vacationRepository.findAllByBetweenDates(startDayOfMonth, endDayOfMonth);
    }

    /**
     * TODO userName select
     */
    private List<FilteredVacation> filterVacationOfMonth(final int year, final int month,
        final List<Vacation> vacations) {
        final LocalDate startDayOfMonth = LocalDate.of(year, month, 1);
        final LocalDate endDayOfMonth = startDayOfMonth.with(lastDayOfMonth());
        final List<FilteredVacation> filtered = new ArrayList<>();

        for (final Vacation vacation : vacations) {
            final LocalDate from = vacation.getStartDateTime().toLocalDate();
            final LocalDate to = vacation.getEndDateTime().toLocalDate();

            int totalUseHour = vacation.getUseHour();
            int startHour = vacation.getStartDateTime().getHour();

            LocalDate cursor = from;
            while (!cursor.isAfter(to)) {
                int use = getUseHour(startHour, totalUseHour);
                if (isInMonth(cursor, startDayOfMonth, endDayOfMonth)) {
                    filtered.add(new FilteredVacation(vacation.getUserId(), cursor.getDayOfMonth(), use));
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

    private List<FilteredVacation> distinctDayVacation(final List<FilteredVacation> filtered) {
        final Map<Integer, Map<String, List<FilteredVacation>>> dayGroups = filtered.stream()
                                                                                    .collect(groupingBy(
                                                                                        FilteredVacation::getDay
                                                                                        , groupingBy(
                                                                                            FilteredVacation::getUserId)
                                                                                    ));

        List<FilteredVacation> result = new ArrayList<>();
        for (Map.Entry<Integer, Map<String, List<FilteredVacation>>> dayEntry : dayGroups.entrySet()) {
            int day = dayEntry.getKey();
            String userId;
            int todayUseHour;
            for (Map.Entry<String, List<FilteredVacation>> userEntry : dayEntry.getValue().entrySet()) {
                userId = userEntry.getKey();
                todayUseHour = userEntry.getValue()
                                   .stream()
                                   .mapToInt(FilteredVacation::getUseHour)
                                   .reduce(Integer::sum).getAsInt();
                result.add(new FilteredVacation(userId, day, todayUseHour));
            }
        }
        return result;
    }
}
