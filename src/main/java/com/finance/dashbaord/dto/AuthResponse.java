package com.finance.dashbaord.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String message;
    private String email;
    private String role;
    private String token;



}
