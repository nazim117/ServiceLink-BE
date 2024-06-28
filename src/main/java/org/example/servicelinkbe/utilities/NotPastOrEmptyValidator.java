package org.example.servicelinkbe.utilities;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class NotPastOrEmptyValidator implements ConstraintValidator<NotPastOrEmpty, LocalDateTime> {
    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        if (localDateTime == null) {
            return false;
        }
        return !localDateTime.isBefore(LocalDateTime.now());
    }
}
