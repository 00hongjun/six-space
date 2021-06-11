package com.sixshop.sixspace.vacation.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {

    private String message = "SUCCESS";
    @JsonInclude(Include.NON_EMPTY)
    private T data;

    public ApiResponse(T data) {
        this.data = data;
    }

}
