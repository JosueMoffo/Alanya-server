package com.example.Alanya_server.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    private String phoneNumber;
    private String password;

}
