package com.clonnit.demo.controller;

import com.clonnit.demo.dto.AuthResponseDto;
import com.clonnit.demo.dto.LoginRequestDto;
import com.clonnit.demo.dto.RegisterRequestDto;
import com.clonnit.demo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    final private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequestDto registerRequest) {
        authService.signup(registerRequest);

        return new ResponseEntity<>("Registo de usuário completo", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody LoginRequestDto loginRequest) {
        return authService.login(loginRequest);
    }
}
