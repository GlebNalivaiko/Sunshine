package by.sunshine.dto;

import by.sunshine.annotation.Existence;
import by.sunshine.annotation.TrimLength;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import static by.sunshine.constant.Error.*;

@Data
public class RegistrationRequestUser {

    @NotBlank(message = EMPTY_NAME)
    @NotNull
    private String name;

    @Email(message = INVALID_EMAIL)
    @Existence(message = ALREADY_EXIST)
    @NotNull
    private String email;

    @TrimLength(message = INVALID_TRIM)
    @NotNull
    private String password;

    @TrimLength(message = INVALID_TRIM)
    @NotNull
    private String confirmedPassword;
}
