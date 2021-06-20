package com.sixshop.sixspace.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 0 ~ 100 예약
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 200 ~> 회원
    NOT_FOUND_USER(200, HttpStatus.NOT_FOUND, "회원을 조회하지 못했습니다."),

    // 300 ~> 휴가
    NOT_FOUND_VACATION(300, HttpStatus.NOT_FOUND, "휴가를 조회하지 못했습니다.");

    private final Integer internalCode;
    private final HttpStatus status;
    private final String message;

    public int getStatusValue() {
        return status.value();
    }

}
