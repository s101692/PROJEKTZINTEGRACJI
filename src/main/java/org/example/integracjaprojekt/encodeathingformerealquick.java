package org.example.integracjaprojekt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class encodeathingformerealquick {
    public static void main(String[] args) {

        PasswordEncoder encoder =
                new BCryptPasswordEncoder();

        System.out.println(
                encoder.encode("user123"));
    }
}
