package ru.otusstudy.otuset.utils;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderUtil {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);

    @Test
    void encodePassword() {
        System.out.println(passwordEncoder.encode("admin"));
    }
}
