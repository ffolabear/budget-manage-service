package com.example.budgetmanageservice.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/login")
@Controller
public class SecurityController {

    @GetMapping
    public void loginGet(String error, String logout) {
        log.info("login get................");
        log.info("logout : " + logout);

    }

}
