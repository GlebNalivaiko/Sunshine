package by.sunshine.validator;

import by.sunshine.annotation.TrimLength;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LengthValidator implements ConstraintValidator<TrimLength, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.trim().length() >= 3 && !value.trim().contains(" ");
    }
}