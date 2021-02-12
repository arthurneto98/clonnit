package com.clonnit.demo.service;

import com.clonnit.demo.dto.AuthResponseDto;
import com.clonnit.demo.dto.LoginRequestDto;
import com.clonnit.demo.dto.RegisterRequestDto;
import com.clonnit.demo.model.User;
import com.clonnit.demo.repository.UserRepository;
import com.clonnit.demo.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuthService {
    final private PasswordEncoder passwordEncoder;
    final private UserRepository userRepository;
    final private AuthenticationManager authenticationManager;
    final private JwtProvider jwtProvider;

    @Transactional
    public void signup(RegisterRequestDto registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(LocalDateTime.now());
        userRepository.save(user);
    }

    public AuthResponseDto login(LoginRequestDto loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);

        return new AuthResponseDto(token, loginRequest.getUsername());
    }
}
