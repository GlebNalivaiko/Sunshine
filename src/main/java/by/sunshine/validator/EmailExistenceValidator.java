package by.sunshine.validator;

import by.sunshine.annotation.Existence;
import by.sunshine.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailExistenceValidator implements ConstraintValidator<Existence, String> {
    private final UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.existsByEmail(email);
    }
}
