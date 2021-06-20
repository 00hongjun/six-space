package com.sixshop.sixspace.vacation.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sixshop.sixspace.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private String statue;
    @JsonInclude(Include.NON_EMPTY)
    private String message;
    @JsonInclude(Include.NON_EMPTY)
    private T data;

    public static <T> ApiResponse SUCCESS(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.statue = "SUCCESS";
        response.data = data;
        return response;
    }

    public static <T> ApiResponse ERROR(ErrorCode errorCode) {
        ApiResponse<T> response = new ApiResponse<>();
        response.statue = "ERROR";
        response.message = errorCode.name();
        return response;
    }

}
