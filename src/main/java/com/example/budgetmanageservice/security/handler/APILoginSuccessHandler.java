package com.example.budgetmanageservice.security.handler;

import com.example.budgetmanageservice.common.utils.JWTUtil;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Slf4j
@RequiredArgsConstructor
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        log.info("Login Success Handler =====================================");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        log.info(String.valueOf(authentication));
        log.info(String.valueOf(authentication.getName()));
        Map<String, Object> claim = Map.of("mid", authentication.getName());
        try {
            //Access Token 유효기간 1일
            String accessToken = jwtUtil.generateToken(claim, 1);
            //Refresh Token 유효기간 30일
            String refreshToken = jwtUtil.generateToken(claim, 30);
            Gson gson = new Gson();
            Map<String, String> keyMap = Map.of("accessToken", accessToken, "refreshToken", refreshToken);
            String jsonStr = gson.toJson(keyMap);
            response.getWriter().println(jsonStr);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
}
