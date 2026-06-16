package org.example.integracjaprojekt.service;

import lombok.RequiredArgsConstructor;
import org.example.integracjaprojekt.dto.LoginRequest;
import org.example.integracjaprojekt.dto.LoginResponse;
import org.example.integracjaprojekt.security.CustomUserDetailsService;
import org.example.integracjaprojekt.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final CustomUserDetailsService userDetailsService;

    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()));

        UserDetails user =
                userDetailsService.loadUserByUsername(
                        request.username());

        String token =
                jwtService.generateToken(user);

        return new LoginResponse(token);
    }
}
