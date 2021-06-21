package com.sixshop.sixspace.vacation.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public Vacations bringVacationByRange(LocalDateTime start, LocalDateTime end) {
        List<Vacation> collect = vacations.stream()
            .filter(v -> v.getStartDateTimeValue().isAfter(start)
                && v.getEndDateTimeValue().isBefore(end))
            .collect(Collectors.toList());

        return Vacations.of(collect);
    }

    public int bringTotalUseHour() {
        return vacations.stream()
            .mapToInt(Vacation::getUseHour)
            .sum();
    }

    public Vacations bringUpcomingVacation(LocalDateTime criteria) {
        List<Vacation> collect = vacations.stream()
            .filter(v -> v.getStartDateTimeValue().isAfter(criteria))
            .collect(Collectors.toList());
        return of(collect);
    }

    public Vacations getUseDaily(LocalDate localDate) { // 연차
        List<Vacation> collect = vacations.stream()
            .filter(Vacation::isDailyVacation)
            .filter(v -> v.isIncludeDatePeriod(localDate))
            .collect(Collectors.toList());
        return Vacations.of(collect);
    }

    public Vacations getUseDailyHour(LocalDate localDate) { // 시차
        List<Vacation> collect = vacations.stream()
            .filter(v -> !v.isDailyVacation())
            .filter(v -> v.isIncludeDatePeriod(localDate))
            .collect(Collectors.toList());
        return Vacations.of(collect);
    }

}
