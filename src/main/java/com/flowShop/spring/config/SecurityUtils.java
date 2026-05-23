package com.flowShop.spring.config;

import com.flowShop.spring.model.User;
import com.flowShop.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }

        System.out.println("AUTH NAME = " + authentication.getName());
        System.out.println("AUTH PRINCIPAL CLASS = " + authentication.getPrincipal().getClass().getName());

        Object principal = authentication.getPrincipal();

        if (principal instanceof User user) {
            return user;
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }
}