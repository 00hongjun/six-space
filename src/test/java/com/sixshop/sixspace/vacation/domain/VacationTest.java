package com.sixshop.sixspace.vacation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VacationTest {

    @DisplayName("연차인지 판단")
    @Test
    void isDailyVacation() {
        // given
        VacationLocalDateTime startTime = VacationLocalDateTime.of(LocalDateTime.of(2021, 06, 19, 9, 0));
        VacationLocalDateTime endTime = VacationLocalDateTime.of(LocalDateTime.of(2021, 06, 19, 18, 0));

        // when
        Vacation vacation = new Vacation("A", startTime, endTime, 8);

        // then
        assertThat(vacation.isDailyVacation())
            .isTrue();
    }

    @DisplayName("주어진 날짜가 휴가 기간 일정에 포함되는지 판단")
    @Test
    void isIncludeDatePeriod() {
        // given
        LocalDate localDate = LocalDate.of(2021, 6, 18);
        VacationLocalDateTime startTime = VacationLocalDateTime.of(LocalDateTime.of(2021, 6, 18, 9, 0));
        VacationLocalDateTime endTime = VacationLocalDateTime.of(LocalDateTime.of(2021, 6, 19, 18, 0));

        // when
        Vacation vacation = new Vacation("A", startTime, endTime, 16);

        // then
        assertThat(vacation.isIncludeDatePeriod(localDate))
            .isTrue();
    }

}