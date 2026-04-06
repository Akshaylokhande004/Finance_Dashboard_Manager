package com.finance.dashbaord.controller;

import com.finance.dashbaord.dto.AuthResponse;
import com.finance.dashbaord.dto.LoginRequest;
import com.finance.dashbaord.dto.RegisterRequest;
import com.finance.dashbaord.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest registerRequest)
    {
        authService.register(registerRequest);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest)
    {
        return  authService.login(loginRequest);

    }

}
