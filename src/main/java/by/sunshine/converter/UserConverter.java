package by.sunshine.converter;

import by.sunshine.entity.User;
import by.sunshine.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserConverter {

    public CustomUserDetails convert(User user) {
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setEmail(user.getEmail());
        customUserDetails.setPassword(user.getPassword());
        customUserDetails.setAuthorities(Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName())));
        return customUserDetails;
    }
}
