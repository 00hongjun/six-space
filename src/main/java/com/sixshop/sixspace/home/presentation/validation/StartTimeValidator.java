package com.sixshop.sixspace.home.presentation.validation;

import com.sixshop.sixspace.home.presentation.validation.annotation.StartTime;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StartTimeValidator implements ConstraintValidator<StartTime, String> {

    private static final Pattern TIME_PATTERN = Pattern.compile("^([1-9]|[01][0-9]|2[0-3]):([0-5][0-9])$");

    @Override
    public void initialize(final StartTime constraintAnnotation) {
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
        return TIME_PATTERN.matcher(value).find();
    }
}
