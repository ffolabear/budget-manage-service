package com.example.budgetmanageservice.user.controller;

import com.example.budgetmanageservice.common.dto.ResponseDto;
import com.example.budgetmanageservice.user.dto.UserRegisterRequestDto;
import com.example.budgetmanageservice.user.dto.UserRegisterResponseDto;
import com.example.budgetmanageservice.user.service.UserService;
import com.example.budgetmanageservice.user.utils.UriCreator;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@Controller
public class UserController {

    private static final String URL = "/api/users";
    private final UserService service;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ResponseDto<UserRegisterResponseDto>> postUser(
            @RequestBody @Valid UserRegisterRequestDto post) {
        ResponseDto<UserRegisterResponseDto> result = service.saveUser(post);
        URI location = UriCreator.createUri(URL, result.getData().userId());
        return ResponseEntity.created(location).body(result);
    }

}
