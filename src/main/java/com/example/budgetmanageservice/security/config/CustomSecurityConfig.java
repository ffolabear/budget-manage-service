package com.example.budgetmanageservice.security.config;

import com.example.budgetmanageservice.common.utils.JWTUtil;
import com.example.budgetmanageservice.security.filter.APILoginFilter;
import com.example.budgetmanageservice.security.filter.TokenCheckFilter;
import com.example.budgetmanageservice.security.handler.APILoginSuccessHandler;
import com.example.budgetmanageservice.security.handler.Custom403Handler;
import com.example.budgetmanageservice.security.service.APIUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class CustomSecurityConfig {

    private final APIUserDetailService apiUserDetailService;
    private final JWTUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //정적 자원 호출시 제외
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        log.info("=============== web configure ===============");
        return (web -> web.ignoring().requestMatchers(PathRequest.
                toStaticResources().atCommonLocations()));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("=============== configure ===============");

        //Authentication 설정
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(apiUserDetailService)
                .passwordEncoder(passwordEncoder());

        //get Authentication
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        http.authenticationManager(authenticationManager);

        //API login filter
        APILoginFilter apiLoginFilter = new APILoginFilter("/generateToken");
        apiLoginFilter.setAuthenticationManager(authenticationManager);

        //API Login Success handler
        APILoginSuccessHandler successHandler = new APILoginSuccessHandler(jwtUtil);
        //Success handler 세팅
        apiLoginFilter.setAuthenticationSuccessHandler(successHandler);
        //APILoginFilter 위치지정
        http.addFilterBefore(apiLoginFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(tokenCheckFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationManager(authenticationManager);

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new Custom403Handler();
    }

    private TokenCheckFilter tokenCheckFilter(JWTUtil jwtUtil) {
        return new TokenCheckFilter(jwtUtil);
    }

}
