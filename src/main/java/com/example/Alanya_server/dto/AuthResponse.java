package com.example.Alanya_server.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthResponse {
    private String accessToken;
    private Long userId;

    public AuthResponse(String accessToken, Long userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }

}
