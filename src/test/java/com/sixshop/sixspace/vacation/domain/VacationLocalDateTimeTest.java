package com.sixshop.sixspace.vacation.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class VacationLocalDateTimeTest {

    private static final LocalDate localDate = LocalDate.of(2021, 5, 22);
    private static final LocalTime localTime = LocalTime.of(13, 0);

    private Stream<Arguments> generateDayVacation() {
        return Stream.of(
            Arguments.of(1, 8),
            Arguments.of(2, 16),
            Arguments.of(3, 24),
            Arguments.of(4, 32),
            Arguments.of(5, 40),
            Arguments.of(10, 80)
        );
    }

    @DisplayName("주입 받은 LocalDateTime 과 동일한 시간을 관리")
    @Test
    void of_dateTime() {
        // given
        LocalDateTime compare = LocalDateTime.of(localDate, localTime);

        // when
        VacationLocalDateTime target = VacationLocalDateTime.of(compare);

        // then
        assertThat(target.getTime())
            .isEqualTo(compare);
    }

    @DisplayName("주입 받은 LocalDate, LocalTime 과 동일한 시간을 관리")
    @Test
    void of_date_time() {
        // given
        LocalDateTime compare = LocalDateTime.of(localDate, localTime);

        // when
        VacationLocalDateTime target = VacationLocalDateTime.of(localDate, localTime);

        // then
        assertThat(target.getTime())
            .isEqualTo(compare);
    }

    @DisplayName("of()는 parm 타입 관계없이 동일")
    @Test
    void of() {
        // given
        LocalDateTime compare = LocalDateTime.of(localDate, localTime);

        // when
        VacationLocalDateTime target1 = VacationLocalDateTime.of(localDate, localTime);
        VacationLocalDateTime target2 = VacationLocalDateTime.of(compare);

        // then
        assertThat(target1).isEqualTo(target2);
        assertThat(target1.getTime())
            .isEqualTo(compare);
        assertThat(target2.getTime())
            .isEqualTo(compare);
    }

    @DisplayName("time 필드가 동일하면 같은 객체로 판단한다")
    @Test
    void of_diff_parameter_compare() {
        // given
        VacationLocalDateTime target1 = VacationLocalDateTime.of(localDate, localTime);
        VacationLocalDateTime target2 = VacationLocalDateTime.of(localDate, localTime);
        VacationLocalDateTime target3 = VacationLocalDateTime.of(LocalDateTime.of(localDate, localTime));

        // then
        assertThat(target1)
            .isEqualTo(target2)
            .isEqualTo(target3);
    }

    @DisplayName("초 단위는 버린다")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 5, 10, 20, 30, 40, 50, 59})
    void of_second(int second) {
        // given
        LocalTime localTime = LocalTime.of(9, 10, second);
        LocalTime compare = LocalTime.of(9, 10);

        // when
        VacationLocalDateTime target = VacationLocalDateTime.of(localDate, localTime);

        // then
        System.out.println(target.getTime());
        System.out.println(compare);
        assertThat(target.getTime().toLocalTime())
            .isEqualTo(compare);
    }

    @DisplayName("10분 단위로 설정 가능 - 성공")
    @ParameterizedTest
    @ValueSource(ints = {0, 10, 20, 30, 40, 50})
    void of_minute_success(int minute) {
        // given
        LocalTime localTime = LocalTime.of(9, minute);

        // then
        VacationLocalDateTime.of(localDate, localTime);
    }

    @DisplayName("10분 단위로 설정 가능 - 성공")
    @ParameterizedTest
    @ValueSource(ints = {0, 10, 20, 30, 40, 50})
    void of_minute_success2(int minute) {
        // given
        LocalTime localTime = LocalTime.of(9, minute);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

        // then
        VacationLocalDateTime.of(localDateTime);
    }

    @DisplayName("10분 단위로 설정 가능 - 실패")
    @ParameterizedTest
    @ValueSource(ints = {1, 5, 9, 11, 21, 29, 31, 59})
    void of_minute_fail(int minute) {
        // given
        LocalTime localTime = LocalTime.of(9, minute);

        // then
        assertThatThrownBy(() -> VacationLocalDateTime.of(localDate, localTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("10분 단위로 설정 가능");
    }

    @DisplayName("10분 단위로 설정 가능 - 실패")
    @ParameterizedTest
    @ValueSource(ints = {1, 5, 9, 11, 21, 29, 31, 59})
    void of_minute_fail2(int minute) {
        // given
        LocalTime localTime = LocalTime.of(9, minute);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

        // then
        assertThatThrownBy(() -> VacationLocalDateTime.of(localDateTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("10분 단위로 설정 가능");
    }

    @DisplayName("8시간 이상 사용할 경우 8의 배수 아니면 실패")
    @ParameterizedTest
    @ValueSource(ints = {9, 10, 11, 15, 17, 20, 23, 25, 100})
    void validateOperandHour_userHour(int hour) {
        // given
        VacationLocalDateTime vacation = VacationLocalDateTime.of(localDate, localTime);

        // then
        assertThatThrownBy(() -> vacation.plusHours(hour))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("8시간 이상 사용할 경우 시작 시간이 09:00 아니면 실패")
    @Test
    void validateOperandHour_startTime() {
        // given
        VacationLocalDateTime vacation = VacationLocalDateTime.of(localDate, localTime);

        // then
        assertThatThrownBy(() -> vacation.plusHours(8))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("시간 더하고 비교하기 - 시차")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7})
    void plusHours_under8(int hour) {
        // given
        LocalDateTime start = LocalDateTime.of(localDate, localTime);
        LocalDateTime plus = start.plusHours(hour);

        // when
        VacationLocalDateTime target1 = VacationLocalDateTime.of(start).plusHours(hour);
        VacationLocalDateTime target2 = VacationLocalDateTime.of(plus);

        // then
        assertThat(target1).isEqualTo(target2);
        assertThat(target2.getTime()).isEqualTo(plus);
    }

    @DisplayName("시간 더하고 비교하기 - 연차")
    @MethodSource("generateDayVacation")
    void plusHours_over8(int day, int hour) {
        // given
        LocalDateTime start = LocalDateTime.of(localDate, LocalTime.of(9, 0));
        LocalDateTime end = LocalDateTime.of(localDate.plusDays(day), LocalTime.of(18, 0));

        // when
        VacationLocalDateTime target1 = VacationLocalDateTime.of(start).plusHours(hour);
        VacationLocalDateTime target2 = VacationLocalDateTime.of(end);

        // then
        assertThat(target1).isEqualTo(target2);
        assertThat(target1.getTime()).isEqualTo(end);
    }

}