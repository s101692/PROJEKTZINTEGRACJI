package org.example.integracjaprojekt.controller;

import lombok.RequiredArgsConstructor;
import org.example.integracjaprojekt.dto.LoginRequest;
import org.example.integracjaprojekt.dto.LoginResponse;
import org.example.integracjaprojekt.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        return authService.login(request);
    }
}