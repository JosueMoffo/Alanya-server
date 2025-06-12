package com.example.Alanya_server.service;

import com.example.Alanya_server.dto.AuthResponse;
import com.example.Alanya_server.dto.LoginRequest;
import com.example.Alanya_server.dto.RegisterRequest;
import com.example.Alanya_server.model.User;
import com.example.Alanya_server.repository.UserRepository;
import com.example.Alanya_server.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Collections;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthResponse registerUser(RegisterRequest registerRequest) {
        // Vérification si l'utilisateur existe déjà
        if (userRepository.existsByPhoneNumber(registerRequest.getPhoneNumber())) {
            throw new RuntimeException("ce numero est deja utilise");
        }

        // Création du nouvel utilisateur
        User user = new User();
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUserName(registerRequest.getUserName());

        System.out.println("USER: " +user.getPhoneNumber() + ":" +  user.getPassword());
        // Sauvegarde en base de données
        User savedUser = userRepository.save(user);

        System.out.println("SaVED USER: " +savedUser.getPhoneNumber() + " : " +  savedUser.getPassword() + " : " + savedUser.getUserName() + ": " + savedUser.getUserId());

        // Génération du token sans réauthentifier
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                savedUser.getPhoneNumber(),
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        String jwt = jwtTokenProvider.generateToken(authentication);

        // Retourne le AuthResponse avec le token et l'ID utilisateur
        return new AuthResponse(jwt, savedUser.getUserId());
    }

    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getPhoneNumber(),
                        loginRequest.getPassword()
                )
        );
        String jwt = jwtTokenProvider.generateToken(authentication);

        User user = userRepository.findByPhoneNumber(loginRequest.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("User not found after auth"));
        return new AuthResponse(jwt, user.getUserId());
    }
}