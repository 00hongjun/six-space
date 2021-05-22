package com.sixshop.sixspace.home.presentation.validation.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.sixshop.sixspace.home.presentation.validation.TwoDayValidator;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.Documented;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TwoDayValidator.class)
@Documented
public @interface TwoDay {
    String message() default "유효하지 않은 날입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
