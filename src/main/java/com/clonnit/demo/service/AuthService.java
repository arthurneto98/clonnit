package com.clonnit.demo.service;

import com.clonnit.demo.dto.RegisterRequestDto;
import com.clonnit.demo.model.User;
import com.clonnit.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuthService {
    final private PasswordEncoder passwordEncoder;
    final private UserRepository userRepository;

    @Transactional
    public void signup(RegisterRequestDto registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(LocalDateTime.now());
        userRepository.save(user);
    }
}
