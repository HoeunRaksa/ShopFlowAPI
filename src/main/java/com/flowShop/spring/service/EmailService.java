package com.flowShop.spring.service;

import com.flowShop.spring.model.EmailVerificationCode;
import com.flowShop.spring.repository.EmailCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final EmailCodeRepository emailCodeRepository;
    public void sendCode(String email) {

        Optional<EmailVerificationCode> existing =
                emailCodeRepository.findTopByEmailOrderByIdDesc(email);
        if (existing.isPresent()) {
            EmailVerificationCode last = existing.get();
            if (last.getExpiredAt().minusMinutes(4).isAfter(LocalDateTime.now())) {
                throw new RuntimeException("Please wait before requesting another code");
            }
        }

        String code = String.valueOf((int)(Math.random() * 900000) + 100000);

        EmailVerificationCode otp = new EmailVerificationCode();
        otp.setEmail(email);
        otp.setCode(code);
        otp.setExpiredAt(LocalDateTime.now().plusMinutes(5));
        otp.setUsed(false);
        otp.setCreatedAt(LocalDateTime.now());
        emailCodeRepository.save(otp);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("OTP Code");
        message.setText("Your OTP is: " + code);

        mailSender.send(message);

    }

    public void verifyCode(String email, String code) {

        EmailVerificationCode otp = emailCodeRepository
                .findTopByEmailAndCodeAndUsedFalseOrderByIdDesc(email, code)
                .orElseThrow(() -> new RuntimeException("Invalid OTP"));

        if (otp.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        otp.setUsed(true);
        emailCodeRepository.save(otp);
    }
}