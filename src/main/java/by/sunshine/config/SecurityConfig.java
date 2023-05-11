package by.sunshine.config;

import by.sunshine.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/logout","/security/**", "/cart/**", "/error**", "/products/{id}", "/products/{id}/{activePage}", "/currency/{id}", "/resources/**", "/flag-icon-css/**", "/img/**", "/**.css", "/**.js", "/sunshine-by")
                        .permitAll()
                        .requestMatchers("/order/**", "/order-cart/**")
                        .hasRole("USER")
                        .requestMatchers("/products/admin", "/admin")
                        .hasRole("ADMIN")
                        .anyRequest()
                        .authenticated().and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class))
                .formLogin()
                .loginPage("/security/authorization_check")
                .successForwardUrl("/sunshine-by")
                .and()
                .logout().invalidateHttpSession(true).deleteCookies("token")
                .logoutUrl("/logout")
                .logoutSuccessUrl("/sunshine-by")
                .permitAll();
        return httpSecurity.build();
    }
}
