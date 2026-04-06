package com.finance.dashbaord.service;

import com.finance.dashbaord.config.PasswordConfig;
import com.finance.dashbaord.dto.AuthResponse;
import com.finance.dashbaord.dto.LoginRequest;
import com.finance.dashbaord.dto.RegisterRequest;
import com.finance.dashbaord.entity.Role;
import com.finance.dashbaord.entity.Status;
import com.finance.dashbaord.entity.User;
import com.finance.dashbaord.repository.UserRepository;
import com.finance.dashbaord.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void register(RegisterRequest registerRequest)
    {
        //check if user exists
        userRepository.findByEmail(registerRequest.getEmail())
                .ifPresent(user -> {
                    throw new RuntimeException("Email already in use");
                });

        //create user
        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.VIEWER)
                .status(Status.ACTIVE)
                .build();
        userRepository.save(user);
    }
    public AuthResponse login (LoginRequest loginRequest)
    {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Password does not match");
        }
        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );
        return AuthResponse.builder()
                .message("Login Succesful")
                .email(loginRequest.getEmail())
                .role(user.getRole().name())
                .token(token)
                .build();
    }


}
