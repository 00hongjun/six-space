package com.sixshop.sixspace.slack.domain;

import com.sixshop.sixspace.exception.IllegalFormatSlackCommandException;
import java.time.LocalDate;
import java.util.Arrays;

public enum TwoDays {
    TODAY("오늘", 0)
    , TOMORROW("내일", 1)
    ;

    private final String text;

    private final int plusDay;

    TwoDays(final String text, final int plusDay) {
        this.text = text;
        this.plusDay = plusDay;
    }

    public static TwoDays findByText(final String value) {
        return Arrays.stream(values())
                     .filter(twoDay -> twoDay.text.equalsIgnoreCase(value))
                     .findFirst()
                     .orElseThrow(() -> new IllegalFormatSlackCommandException("잘못된 휴가 커맨드 양식입니다. 오늘 | 내일만 입력하실 수 있습니다."));
    }

    public LocalDate toLocalDate() {
        return LocalDate.now().plusDays(this.plusDay);
    }
}
