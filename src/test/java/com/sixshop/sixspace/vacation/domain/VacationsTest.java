package com.sixshop.sixspace.vacation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VacationsTest {

    private Vacation v1 = new Vacation();

    @DisplayName("연차인 휴가만 필터링")
    @Test
    void isDailyVacation() {
        // given
        LocalDate localDate = LocalDate.of(2121, 6, 19);
        VacationLocalDateTime startTime = VacationLocalDateTime.of(LocalDateTime.of(localDate, LocalTime.of(9, 0)));
        VacationLocalDateTime endTime1 = VacationLocalDateTime.of(LocalDateTime.of(localDate, LocalTime.of(18, 0)));
        VacationLocalDateTime endTime2 = VacationLocalDateTime.of(LocalDateTime.of(localDate, LocalTime.of(15, 0)));

        Vacation vacation1 = new Vacation("A", startTime, endTime1, 8);
        Vacation vacation2 = new Vacation("A", startTime, endTime2, 8);

        // when
        Vacations vacations = Vacations.of(Arrays.asList(vacation1, vacation2));

        // then
        assertThat(vacations.getUseDaily(localDate).vacations)
            .contains(vacation1);
    }

    @DisplayName("시차 휴가만 필터링")
    @Test
    void isIncludeDatePeriod() {
        // given
        LocalDate localDate = LocalDate.of(2121, 6, 19);
        VacationLocalDateTime startTime = VacationLocalDateTime.of(LocalDateTime.of(localDate, LocalTime.of(9, 0)));
        VacationLocalDateTime endTime1 = VacationLocalDateTime.of(LocalDateTime.of(localDate, LocalTime.of(18, 0)));
        VacationLocalDateTime endTime2 = VacationLocalDateTime.of(LocalDateTime.of(localDate, LocalTime.of(15, 0)));

        Vacation vacation1 = new Vacation("A", startTime, endTime1, 8);
        Vacation vacation2 = new Vacation("A", startTime, endTime2, 8);

        // when
        Vacations vacations = Vacations.of(Arrays.asList(vacation1, vacation2));

        // then
        assertThat(vacations.getUseDailyHour(localDate).vacations)
            .contains(vacation2);
    }

}