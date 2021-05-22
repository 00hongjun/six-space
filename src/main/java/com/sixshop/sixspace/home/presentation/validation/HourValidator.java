package com.sixshop.sixspace.home.presentation.validation;

import com.sixshop.sixspace.home.presentation.validation.annotation.Hour;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HourValidator implements ConstraintValidator<Hour, String> {

    private static final Pattern HOUR_PATTERN = Pattern.compile("^([1-8]|[0][1-8])$");

    @Override
    public void initialize(final Hour constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (Objects.isNull(value) || !isTime(value)) {
            return false;
        }
        return true;
    }

    private boolean isTime(final String value) {
        return HOUR_PATTERN.matcher(value).find();
    }
}
