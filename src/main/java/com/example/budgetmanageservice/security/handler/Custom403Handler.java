package com.example.budgetmanageservice.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Custom403Handler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        //JSON 요청이었는지
        String contentType = request.getHeader("Content-type");
        boolean jsonRequest = contentType.startsWith("application/json");
        log.info("isJson : " + jsonRequest);

        //일반 요청
        if (!jsonRequest) {
            response.sendRedirect("member/login?error=ACCESS_DENIED");
        }
    }
}
