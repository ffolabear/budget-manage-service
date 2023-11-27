package com.example.budgetmanageservice.user.controller;

import io.swagger.annotations.ApiOperation;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/api/test")
@RestController
public class TestController {

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get")
    public void getTest() {
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/read")
    public void getTestRead() {
    }

    @ApiOperation("sample GET doa")
    @GetMapping("/doA")
    private List<String> doA() {
        return Arrays.asList("AAA", "BBB", "CCC");
    }
}
