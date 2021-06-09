package com.sixshop.sixspace.vacation.service;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

import com.sixshop.sixspace.vacation.domain.Vacations;
import com.sixshop.sixspace.vacation.repository.DayOfMonthVacationRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacationService {

    private static final int WEEK_DAYS = 7;
    private static final int LUNCH_HOUR = 13;
    private static final int FINISH_WORK_HOUR = 18;
    private static final LocalTime TODAY_LAST_TIME = LocalTime.of(23, 59);
    private final DayOfMonthVacationRepository dayOfMonthVacationRepositorynRepository;

    public void getAboutMonthDuration(String userId) {
        LocalDate now = LocalDate.now();
        LocalDate lastDay = getAboutMonthStartDate(now);
        LocalDate firstDay = getAboutMonthEndDate(now);

        Vacations vacations = dayOfMonthVacationRepositorynRepository.findAllByBetweenDates2(
            LocalDateTime.of(firstDay, LocalTime.MIDNIGHT),
            LocalDateTime.of(lastDay, TODAY_LAST_TIME));

        int weekUseVacationHour = vacations.bringWeekUseVacationHour(now);
        int monthUseVacationHour = vacations.bringMonthVacationHour(now);
    }

    private LocalDate getAboutMonthStartDate(LocalDate now) {
        LocalDate lastDay = now.with(lastDayOfMonth());

        if (ChronoUnit.DAYS.between(now, lastDay) < WEEK_DAYS) {
            lastDay = now.plusDays(WEEK_DAYS);
        }

        return lastDay;
    }

    private LocalDate getAboutMonthEndDate(LocalDate now) {
        LocalDate firstDay = now.with(firstDayOfMonth());

        if (ChronoUnit.DAYS.between(now, firstDay) < WEEK_DAYS) {
            firstDay = now.minusDays(WEEK_DAYS);
        }

        return firstDay;
    }

}
