package com.sixshop.sixspace.exception.handler;

import java.util.Objects;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RequestValidationHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> constraintViolation(ConstraintViolationException e) {
        final String errorMessage = e.getConstraintViolations().stream()
                                  .map(ConstraintViolation::getMessageTemplate)
                                  .findAny()
                                  .orElse("VALIDATION ERROR");

        log.info("RequestValidationAdviser - ConstraintViolationException : {} : {}", HttpStatus.BAD_REQUEST.value(), errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                             .body(errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValid(MethodArgumentNotValidException e) {
        final BindingResult bindingResult = e.getBindingResult();
        final FieldError fieldError = bindingResult.getFieldError();

        String errorMessage = "VALIDATION ERROR";
        if (Objects.nonNull(fieldError)) {
            errorMessage = fieldError.getDefaultMessage();
        }

        log.info("RequestValidationAdviser - MethodArgumentNotValidException : {} : {}", HttpStatus.BAD_REQUEST.value(), errorMessage);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
            .body(errorMessage);
    }
}
