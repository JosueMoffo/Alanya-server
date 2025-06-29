package com.example.Alanya_server.security;

import com.example.Alanya_server.model.User;
import com.example.Alanya_server.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String phonenumber) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(phonenumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with phone : " + phonenumber));

        return UserPrincipal.create(user);
    }
}
