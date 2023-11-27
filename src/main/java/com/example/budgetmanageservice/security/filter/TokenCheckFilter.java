package com.example.budgetmanageservice.security.filter;

import com.example.budgetmanageservice.common.utils.JWTUtil;
import com.example.budgetmanageservice.security.exception.AccessTokenException;
import com.example.budgetmanageservice.security.exception.AccessTokenException.TOKEN_ERROR;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.filter.OncePerRequestFilter;

@Log4j2
@RequiredArgsConstructor
public class TokenCheckFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (!path.startsWith("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("Token Check Filter...................");
        log.info("JWTUtil : " + jwtUtil);

        try {
            validateAccessToken(request);
            filterChain.doFilter(request, response);
        } catch (AccessTokenException accessTokenException) {
            accessTokenException.sendResponseError(response);
        }
    }

    private Map<String, Object> validateAccessToken(HttpServletRequest request) throws AccessTokenException {
        String headStr = request.getHeader("Authorization");
        if (headStr == null || headStr.length() < 8) {
            throw new AccessTokenException(TOKEN_ERROR.UNACCEPT);
        }

        //Bearer 생략
        String tokenType = headStr.substring(0, 6);
        String tokenStr = headStr.substring(7);

        if (!tokenType.equalsIgnoreCase("Bearer")) {
            throw new AccessTokenException(TOKEN_ERROR.BADTYPE);
        }

        try {
            Map<String, Object> values = jwtUtil.validateToken(tokenStr);
            return values;
        } catch (MalformedJwtException malformedJwtException) {
            log.error("MalformedJwtException----------------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.MALFORM);
        } catch (SignatureException signatureException) {
            log.error("SignatureException----------------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADSIGN);
        } catch (ExpiredJwtException expiredJwtException) {
            log.error("ExpiredJwtException----------------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.EXPIRED);
        }
    }
}
