package com.sixshop.sixspace.vacation.domain;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class Vacations {

    final List<Vacation> vacations;

    private Vacations(List<Vacation> vacations) {
        this.vacations = Collections.unmodifiableList(vacations);
    }

    public static Vacations of(List<Vacation> vacations) {
        return new Vacations(vacations);
    }

    public Vacations bringUpcomingVacation(LocalDateTime criteria) {
        List<Vacation> collect = vacations.stream()
            .filter(v -> v.getStartDateTimeValue().isAfter(criteria))
            .collect(Collectors.toList());
        return of(collect);
    }

    public int bringMonthVacationHour(LocalDate criteria) {
        LocalDateTime start = LocalDateTime.of( // 월의 시작
            criteria.with(firstDayOfMonth()),
            LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of( // 월의 종료
            criteria.with(lastDayOfMonth()),
            LocalTime.MAX);

        return vacations.stream()
            .filter(v -> v.getStartDateTimeValue().isAfter(start)
                && v.getEndDateTimeValue().isBefore(end))
            .mapToInt(Vacation::getUseHour)
            .sum();
    }

    public int bringWeekUseVacationHour(LocalDate criteria) {
        int sundayNumber = 0;
        int saturdayNumber = DayOfWeek.SATURDAY.getValue(); // 6
        int criteriaNumber = criteria.getDayOfWeek().getValue();

        if (criteria.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            criteriaNumber = sundayNumber;
        }

        LocalDateTime start = LocalDateTime.of(  // '주'의 시작
            criteria.minusDays(criteriaNumber - sundayNumber),
            LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of( // '주'의 종료
            criteria.plusDays(saturdayNumber - criteriaNumber),
            LocalTime.MAX);

        return vacations.stream()
            .filter(v -> v.getStartDateTimeValue().isAfter(start)
                && v.getEndDateTimeValue().isBefore(end))
            .mapToInt(Vacation::getUseHour)
            .sum();
    }

}
