package com.flowShop.spring.repository;

import com.flowShop.spring.model.EmailVerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailCodeRepository
        extends JpaRepository<EmailVerificationCode, Long> {

    Optional<EmailVerificationCode>
    findTopByEmailOrderByIdDesc(String email);

    Optional<EmailVerificationCode>
    findTopByEmailAndCodeAndUsedFalseOrderByIdDesc(String email, String code);
}