package com.flowShop.spring.service;

import com.flowShop.spring.Dtos.AuthResponse;
import com.flowShop.spring.Dtos.LoginRequest;
import com.flowShop.spring.Dtos.RegisterRequest;
import com.flowShop.spring.Dtos.UserResponse;
import com.flowShop.spring.Enum.Role;
import com.flowShop.spring.Enum.SubscriptionPlan;
import com.flowShop.spring.model.User;
import com.flowShop.spring.repository.UserRepository;
import com.flowShop.spring.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
   private final EmailService emailService;

    public String login(LoginRequest request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        emailService.sendCode(request.getEmail());

        return "OTP sent to your email";
    }

    public AuthResponse generateTokenAfterOtp(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserResponse userResponse = UserResponse.builder().email(user.getEmail()).firstName(user.getFirstName()).lastName(user.getLastName()).imageUrl(user.getImageUrl()).build();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponse(accessToken,refreshToken, userResponse);
    }

    public String register(RegisterRequest request) {

        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            User user = existingUser.get();

            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            emailService.sendCode(user.getEmail());

            return "User already exists. OTP sent to your email.";
        }
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.FREE)
                .subscriptionPlan(SubscriptionPlan.FREE)
                .verified(false)
                .build();

        userRepository.save(user);

        emailService.sendCode(user.getEmail());

        return "Register success. OTP sent to your email.";
    }

    public AuthResponse refreshToken(String refreshToken) {
        String email = jwtService.extractUsername(refreshToken);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponse userResponse = UserResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .imageUrl(user.getImageUrl())
                .build();

        String newAccessToken = jwtService.generateAccessToken(user);

        return new AuthResponse(
                newAccessToken,
                refreshToken,
                userResponse
        );
    }

}