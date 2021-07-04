package com.sixshop.sixspace.slack.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SlackVacationCommandTest {

    @DisplayName("슬랙 휴가 커맨더에 [오늘|내일]이란 값이 들어오면 성공한다.")
    @ParameterizedTest
    @ValueSource(strings = {"오늘", "내일"})
    void validTwoDay(final String twoDay) {
        // given
        final String message = twoDay + " 14:00 1";
        final TwoDays expect = TwoDays.findByText(twoDay);

        // when
        final SlackVacationCommand actual = new SlackVacationCommand(message);

        //then
        assertThat(actual.getTwoDay()).isEqualTo(expect);
    }

    @DisplayName("슬랙 휴가 커맨더에 [오늘|내일]이 아닌 그외에 잘못된 날을 입력하면 예외를 발생한다.")
    @Test
    void validTwoDay_exception() {
        // given
        final String message = "글피 14:00 1";

        // when then
        assertThatThrownBy(() -> new SlackVacationCommand(message))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("유효한 시작 시간이 들어오면 성공한다.")
    @ParameterizedTest
    @ValueSource(strings = {
        "09:00", "10:00", "11:00", "12:00"
        , "14:00", "15:00", "16:00", "17:00"
    })
    void validStartTime(final String time) {
        // given
        final String message = "오늘 " + time + " 1";
        final int hour = Integer.parseInt(time.split(":")[0]);
        final LocalDateTime expect = TwoDays.TODAY.toLocalDate().atTime(hour, 0);

        // when
        final SlackVacationCommand actual = new SlackVacationCommand(message);

        //then
        assertThat(actual.getStartTime().getTime()).isEqualTo(expect);
    }

    @DisplayName("유효하지 않은 시작 시간이 들어오면 예외 발생을 성공한다.")
    @ParameterizedTest
    @ValueSource(strings = {"13:00", "12:60", "18:00"})
    void validStartTime_exception(final String time) {
        // given
        final String message = "오늘 " + time + " 1";

        // when then
        assertThatThrownBy(() -> new SlackVacationCommand(message))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("사용 시간 0이 아니면 성공한다.")
    @Test
    void validUseHour() {
        // given
        final String message = "오늘 14:00 1";
        final int expect = 1;

        // when
        final SlackVacationCommand actual = new SlackVacationCommand(message);

        //then
        assertThat(actual.getUseHour()).isEqualTo(expect);
    }

    @DisplayName("사용 시간 0이면 예외 발생을 성공한다.")
    @Test
    void validUseHour_exception() {
        // given
        final String message = "오늘 14:00 0";

        // when then
        assertThatThrownBy(() -> new SlackVacationCommand(message))
            .isInstanceOf(IllegalArgumentException.class);
    }
}