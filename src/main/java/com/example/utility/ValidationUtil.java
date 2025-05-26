package com.example.utility;

import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public  class  ValidationUtil {

    public static boolean isEmail(String contact) {
        return contact != null && contact.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }

    public static boolean isMobile(String contact) {
        return contact != null && contact.matches("^[6-9]\\d{9}$");
    }

    public static Optional<String> validateContact(String contact) {

        System.out.println("Validation Check ========>");
        if (contact == null || contact.trim().isEmpty()) {
            return Optional.of("Email or mobile number is required.");
        }
        if (!isEmail(contact) && !isMobile(contact)) {
            return Optional.of("Enter a valid email or 10-digit mobile number starting with 6–9.");
        }
        return Optional.empty();
    }


}
