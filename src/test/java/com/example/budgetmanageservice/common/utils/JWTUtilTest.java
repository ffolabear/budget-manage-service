package com.example.budgetmanageservice.common.utils;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
class JWTUtilTest {

    @Autowired
    private JWTUtil jwtUtil;

    @Test
    void testGenerate() throws NoSuchAlgorithmException {
        Map<String, Object> claimMap = Map.of("mid", "ABCDE");
        String jwtStr = jwtUtil.generateToken(claimMap, 1);
        log.info(jwtStr);
    }

    @Test
    void testValidate() {
        String jwtStr = "eyJhbGciOiJIUzUxMiJ9.eyJtaWQiOiJBQkNERSIsImlhdCI6MTcwMTA4NTY3MCwiZXhwIjoxNzAxMDg1NzMwfQ.1YWsarpLVqBMTkrHd_3UeEPobkqoKuY7v2QNqZO-9ajzutjWOY5QnGFvnKQAl5IR8i5zQRhyo9z5EW4qcpkwsg";
        Map<String, Object> claim = jwtUtil.validateToken(jwtStr);
        log.info(claim);
    }

    @Test
    void testAll() throws NoSuchAlgorithmException {
        String jwtStr = jwtUtil.generateToken(Map.of("mid", "AAAA", "email", "aaaa@bbb.com"), 1);
        log.info(jwtStr);
        Map<String, Object> claim = jwtUtil.validateToken(jwtStr);
        log.info("MID : " + claim.get("mid"));
        log.info("email : " + claim.get("email"));
    }
}