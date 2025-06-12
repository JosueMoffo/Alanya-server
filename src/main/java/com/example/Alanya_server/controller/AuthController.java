package com.example.Alanya_server.controller;

import com.example.Alanya_server.dto.AuthResponse;
import com.example.Alanya_server.dto.LoginRequest;
import com.example.Alanya_server.dto.RegisterRequest;
import com.example.Alanya_server.model.User;
import com.example.Alanya_server.repository.UserRepository;
import com.example.Alanya_server.security.JwtTokenProvider;
import com.example.Alanya_server.service.AuthService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(
            AuthService authService,
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        System.out.println("INITT REGISTER");
        AuthResponse response = authService.registerUser(registerRequest);
        return ResponseEntity.ok(response);
    }
}