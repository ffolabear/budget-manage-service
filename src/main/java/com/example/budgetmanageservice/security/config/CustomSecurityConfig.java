package com.example.budgetmanageservice.security.config;

import com.example.budgetmanageservice.security.handler.Custom403Handler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Slf4j
@RequiredArgsConstructor
@EnableMethodSecurity
@Configuration
public class CustomSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("=============== configure ===============");
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(
                        (formLogin) -> formLogin.loginPage("/member/login"))
                .exceptionHandling(handler -> handler.accessDeniedHandler(accessDeniedHandler()));

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new Custom403Handler();
    }

    //정적 자원 호출시 제외
    public WebSecurityCustomizer webSecurityCustomizer() {
        log.info("=============== web configure ===============");
        return (web -> web.ignoring().requestMatchers(PathRequest.
                toStaticResources().atCommonLocations()));
    }
}
