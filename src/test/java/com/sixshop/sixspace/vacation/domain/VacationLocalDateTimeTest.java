package com.sixshop.sixspace.vacation.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class VacationLocalDateTimeTest {

    private static final LocalDate localDate = LocalDate.of(2021, 5, 22);
    private static final LocalTime localTime = LocalTime.of(13, 0);

    @DisplayName("주입 받은 LocalDateTime 과 동일한 시간을 관리")
    @Test
    void of() {
        // given
        LocalDateTime compare = LocalDateTime.of(localDate, localTime);

        // when
        VacationLocalDateTime target = VacationLocalDateTime.of(compare);

        // then
        assertThat(target.getTime())
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

    @DisplayName("시간 더하고 비교하기")
    @Test
    void plusHours() {
        // given
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        LocalDateTime plus1 = localDateTime.plusHours(1);

        // when
        VacationLocalDateTime target1 = VacationLocalDateTime.of(plus1);
        VacationLocalDateTime target2 = VacationLocalDateTime.of(localDateTime).plusHours(1);

        // then
        assertThat(target1).isEqualTo(target2);
        assertThat(target2.getTime()).isEqualTo(plus1);
    }

    @DisplayName("휴가 사용 시간이 퇴근 시간이 넘는 경우, 1시간 미만이 남으면 버린다")
    @Test
    void plusHours_remain_under_1hour() {
        // given
        LocalTime start = LocalTime.of(17, 30);
        VacationLocalDateTime target = VacationLocalDateTime.of(localDate, start);

        // when
        target = target.plusHours(1);

        // then
        assertThat(target.getTime())
            .isEqualTo(LocalDateTime.of(localDate, LocalTime.of(18, 0)));
    }

    @DisplayName("휴가 사용 시간이 퇴근 시간이 넘는 경우, 1시간 미만이 남으면 버린다")
    @Test
    void plusHours_remain_over_1hour() {
        // given
        LocalTime start = LocalTime.of(17, 30);
        VacationLocalDateTime target = VacationLocalDateTime.of(localDate, start);

        // when
        target = target.plusHours(1);

        // then
        assertThat(target.getTime())
            .isEqualTo(LocalDateTime.of(localDate, LocalTime.of(18, 0)));
    }

    @DisplayName("다음날로 넘어갈 경우 1시간 밑으로는 버리고 시간 추가")
    @Test
    void plusHours_remain_over_hour() {
        // given
        VacationLocalDateTime target1 = VacationLocalDateTime.of(localDate, LocalTime.of(17, 0));
        VacationLocalDateTime target2 = VacationLocalDateTime.of(localDate, LocalTime.of(17, 30));
        VacationLocalDateTime expect = VacationLocalDateTime.of(localDate.plusDays(1), LocalTime.of(11, 0));

        // when
        target1 = target1.plusHours(3);
        target2 = target2.plusHours(3);

        // then
        assertThat(target1).isEqualTo(expect);
        assertThat(target2).isEqualTo(expect);
        assertThat(target1).isEqualTo(target2);
    }

    @DisplayName("시작시간 == 출근시간")
    @Test
    void isStartGoOfficeTime() {
        // given
        LocalTime startTime = LocalTime.of(9, 0);
        LocalDateTime start = LocalDateTime.of(LocalDate.of(2021, 6, 20), startTime);

        // when
        VacationLocalDateTime vacationTime = VacationLocalDateTime.of(start);

        // then
        assertThat(vacationTime.isStartGoOfficeTime())
            .isTrue();
    }

    @DisplayName("종료시간 == 퇴근시간")
    @Test
    void isEndLeaveOfficeTime() {
        // given
        LocalTime startTime = LocalTime.of(18, 0);
        LocalDateTime start = LocalDateTime.of(LocalDate.of(2021, 6, 20), startTime);

        // when
        VacationLocalDateTime vacationTime = VacationLocalDateTime.of(start);

        // then
        assertThat(vacationTime.isEndLeaveOfficeTime())
            .isTrue();
    }

}