package com.sixshop.sixspace.home.presentation.validation.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.sixshop.sixspace.home.presentation.validation.HourValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HourValidator.class)
@Documented
public @interface Hour {
    String message() default "유효하지 않은 시간입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
