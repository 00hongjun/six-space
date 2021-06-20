package com.sixshop.sixspace.exception.handler;

import com.sixshop.sixspace.exception.ErrorCode;
import com.sixshop.sixspace.exception.ServiceException;
import com.sixshop.sixspace.vacation.presentation.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiResponse<Object>> serviceExceptionHandler(ServiceException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse<Object> response = ApiResponse.ERROR(errorCode);
        return ResponseEntity.status(errorCode.getStatusValue())
            .body(response);
    }

}
