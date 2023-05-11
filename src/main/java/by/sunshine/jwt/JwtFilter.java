package by.sunshine.jwt;

import by.sunshine.dto.UserDto;
import by.sunshine.security.CustomUserDetails;
import by.sunshine.service.CustomUserDetailsService;
import by.sunshine.service.SessionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final SessionService securityService;
    private final CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = "";
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("token")) {
                token = cookie.getValue();
            }
        }
        if (!jwtTokenUtil.validateToken(token)) {
            UserDto userDto = (UserDto) request.getSession().getAttribute("user");
            if (userDto == null || Objects.requireNonNull(userDto).getId() != null) {
                request.getSession().setAttribute("user", new UserDto());
            }
            filterChain.doFilter(request, response);
            return;
        }
        setAuthenticationDetails(request, token);
        securityService.updateSession(request, token);
        filterChain.doFilter(request, response);
    }

    private void setAuthenticationDetails(HttpServletRequest request, String token) {
        CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(jwtTokenUtil.getEmailFromToken(token));//закинули юзера
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());//это и дальше это дефолтная логика

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
