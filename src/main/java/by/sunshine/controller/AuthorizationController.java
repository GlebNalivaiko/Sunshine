package by.sunshine.controller;

import by.sunshine.dto.AccessNumberDto;
import by.sunshine.dto.AuthorizationRequestUser;
import by.sunshine.dto.RegistrationRequestUser;
import by.sunshine.dto.UserDto;
import by.sunshine.entity.User;
import by.sunshine.jwt.JwtTokenUtil;
import by.sunshine.service.EmailService;
import by.sunshine.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

import static by.sunshine.constant.Error.INCORRECT_AUTH;
import static by.sunshine.constant.Error.PASSWORDS_DONT_MATCH;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("security")
public class AuthorizationController {

    private static final int COOKIE_LIFE_SECONDS = 900;
    private static final String PATH = "/";
    private static final String TYPE = "text/plain";
    private static final String COOKIE_NAME = "token";
    private final EmailService emailService;
    private Integer accessNumber;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private RegistrationRequestUser requestUser;


    @GetMapping("authorization_check")
    public String authorization(Model model) {
        model.addAttribute("authorizationRequestUser", new AuthorizationRequestUser());
        return "authorization";
    }

    @GetMapping("registration_check")
    public String registration(Model model) {
        model.addAttribute("registrationRequestUser", new RegistrationRequestUser());
        return "registration";
    }


    @PostMapping("registration")
    public String registration(@Valid RegistrationRequestUser registrationRequestUser,
                               BindingResult errors,
                               Model model) {
        if (errors.hasErrors()) return "registration";
        if (!registrationRequestUser.getPassword().equals(registrationRequestUser.getConfirmedPassword())) {
            model.addAttribute("error", PASSWORDS_DONT_MATCH);
            return "registration";
        }
//        accessNumber = emailService.getAccessNumber(registrationRequestUser.getEmail());
        accessNumber = 1;
        model.addAttribute("access_number_dto", new AccessNumberDto());
        requestUser = registrationRequestUser;
        log.error("qwerty");
        return "access_number_input";
    }

    @PostMapping("authorization")
    public String authorization(@Valid AuthorizationRequestUser authorizationRequestUser,
                                BindingResult errors,
                                Model model,
                                @SessionAttribute("user") UserDto userDto,
                                HttpServletResponse response) {
        if (errors.hasErrors()) {
            model.addAttribute("error", INCORRECT_AUTH);
            return "authorization";
        }
        Optional<User> user = userService.findByEmailAndPassword(authorizationRequestUser);
        if (user.isEmpty()) {
            model.addAttribute("error", INCORRECT_AUTH);
            return "authorization";
        }
        userService.updateUserDto(user.get(), userDto);
        return createCookieAndGetPage(response, user.get());
    }

    @PostMapping("confirmation")
    public String checkCode(AccessNumberDto accessNumberDto,
                            HttpServletResponse response,
                            @SessionAttribute("user") UserDto userDto) {
        log.error("сдесь");
        if (accessNumberDto.getNumber().equals(accessNumber)) {
            User user = userService.save(requestUser, userDto);
            return createCookieAndGetPage(response, user);
        }
        return "access_number_input";
    }

    private String createCookieAndGetPage(HttpServletResponse response, User user) {
        String token = jwtTokenUtil.generateToken(user.getEmail());
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setSecure(true);
        cookie.setPath(PATH);
        cookie.setMaxAge(COOKIE_LIFE_SECONDS);
        response.addCookie(cookie);
        response.setContentType(TYPE);
        return "redirect:/sunshine-by";
    }
}
