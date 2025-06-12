package com.example.Alanya_server.security;

import com.example.Alanya_server.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserPrincipal implements UserDetails {
    // Les champs de UserPrincipal doivent correspondre à ce que UserDetails attend
    private final String username; // Le numéro de téléphone de l'utilisateur
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;


    public UserPrincipal(String username, String password,
                         Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_USER"));

        return new UserPrincipal(
                user.getPhoneNumber(), // <-- Ceci est le USERNAME pour Spring Security (ce qui est utilisé pour loadUserByUsername)
                user.getPassword(),
                authorities
                );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}