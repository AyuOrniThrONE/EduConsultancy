package com.example.UserAuthModule.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.UserAuthModule.entity.VerificationToken;


public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}

