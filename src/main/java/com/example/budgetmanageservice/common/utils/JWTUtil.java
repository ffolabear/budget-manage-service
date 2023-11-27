package com.example.budgetmanageservice.common.utils;

import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class JWTUtil {

    @Value("${JWT_SECRET_KEY}")
    private String key;

    public String generateToken(Map<String, Object> valueMap, int days) throws NoSuchAlgorithmException {
        log.info("generateKey..." + key);

        //헤더부분
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        //payload부분
        Map<String, Object> payload = new HashMap<>();
        payload.putAll(valueMap);

        //테스트시에만 짧은 시간으로 설정
        int time = (60 * 24) * days;

        String jwtStr = Jwts.builder()
                .setClaims(payload)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(time).toInstant()))
                .signWith(convertToKey(key))
                .compact();
        return jwtStr;
    }

    private SecretKey convertToKey(String rawKey) {
        byte[] keyBytes = rawKey.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }


    public Map<String, Object> validateToken(String token) {
        Map<String, Object> claim = null;
        claim = Jwts.parserBuilder().setSigningKey(convertToKey(key))
                .build()
                .parseClaimsJws(token).getBody();
        return claim;
    }

}
