package com.sixshop.sixspace.home.presentation;

import java.util.Arrays;

public enum TwoDays {
    TODAY("오늘")
    , TOMORROW("내일")
    ;

    private final String text;

    TwoDays(final String text) {
        this.text = text;
    }

    public static TwoDays findByText(final String value) {
        return Arrays.stream(values())
                     .filter(twoDay -> twoDay.text.equalsIgnoreCase(value))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("오늘 | 내일만 입력하실 수 있습니다."));
    }
}
