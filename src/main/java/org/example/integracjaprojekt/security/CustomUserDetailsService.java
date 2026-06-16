package org.example.integracjaprojekt.security;

import lombok.RequiredArgsConstructor;
import org.example.integracjaprojekt.model.Uzytkownik;
import org.example.integracjaprojekt.repository.UzytkownikRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UzytkownikRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Uzytkownik user = repository.findByNazwaUzytkownika(username).orElseThrow();
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getNazwaUzytkownika())
                .password(user.getHaslo())
                .roles(user.getRola())
                .build();
    }
}