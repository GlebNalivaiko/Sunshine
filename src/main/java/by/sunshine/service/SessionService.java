package by.sunshine.service;


import by.sunshine.dto.UserDto;
import by.sunshine.entity.User;
import by.sunshine.jwt.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class SessionService {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    /**
     * почему не работает без транзакшионал? Наверное потому что транзакция в jpa по молчанию работает только в эндпоинтах
     * почему фильтр столько рз вызывается
     * почему лагает сайт еси в корзине очень много заказов
     * как на али экспресс отправляют письмо на почту с подтверждением там есть распараллеливание
     * private double valueOfCurrency = 1;  иногда надо D в случае с объекта почему?
     */
    @Transactional
    public void updateSession(HttpServletRequest request, String token) {
        if (request.getSession().getAttribute("user") == null) {
            UserDto userDto = new UserDto();
            User user = userService.findByEmail(jwtTokenUtil.getEmailFromToken(token)).orElseThrow();
            userService.updateUserDto(user, userDto);
            request.getSession().setAttribute("user", userDto);
        }
    }
}
