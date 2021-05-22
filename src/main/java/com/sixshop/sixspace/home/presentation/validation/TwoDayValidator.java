package com.sixshop.sixspace.home.presentation.validation;

import com.sixshop.sixspace.home.presentation.TwoDays;
import com.sixshop.sixspace.home.presentation.validation.annotation.TwoDay;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TwoDayValidator implements ConstraintValidator<TwoDay, String> {

    private static final boolean INVALID = false;
    private static final boolean VALID = true;

    @Override
    public void initialize(final TwoDay constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (Objects.isNull(value) || !findTwoDays(value)) {
            return INVALID;
        }

        return VALID;
    }

    private boolean findTwoDays(final String value) {
        try {
            TwoDays.findByText(value);
            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    private void addConstraint(final ConstraintValidatorContext context, final String errorCode) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorCode)
               .addConstraintViolation();
    }
}
