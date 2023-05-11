package by.sunshine.dto;

import by.sunshine.annotation.TrimLength;
import jakarta.validation.constraints.Email;
import lombok.Data;

import static by.sunshine.constant.Error.INCORRECT_AUTH;
import static by.sunshine.constant.Error.INVALID_EMAIL;

@Data
public class AuthorizationRequestUser {

    @Email(message = INVALID_EMAIL)
    private String email;

    @TrimLength(message = INCORRECT_AUTH)
    private String password;
}
