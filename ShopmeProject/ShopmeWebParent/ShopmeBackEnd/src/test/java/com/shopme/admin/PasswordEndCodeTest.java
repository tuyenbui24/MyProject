package com.shopme.admin;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PasswordEndCodeTest {

    @Test
    public void testEndCodePassword() {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "123456";
        String encodedPassword = encoder.encode(rawPassword);


        System.out.println(encodedPassword);

        boolean matches = encoder.matches(rawPassword, encodedPassword);
        assertThat(matches).isTrue();
    }
}
