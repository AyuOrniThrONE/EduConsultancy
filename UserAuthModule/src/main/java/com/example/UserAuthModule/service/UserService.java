package com.example.UserAuthModule.service;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.UserAuthModule.entity.ERole;
import com.example.UserAuthModule.entity.Role;
import com.example.UserAuthModule.entity.User;
import com.example.UserAuthModule.entity.VerificationToken;
import com.example.UserAuthModule.repository.UserRepository;
import com.example.UserAuthModule.repository.VerificationTokenRepository;

@Service
public class UserService {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private VerificationTokenRepository tokenRepository;
    
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

    public User registerNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        user.setRole(ERole.ROLE_USER);
        return userRepository.save(user);

    }
    public String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        tokenRepository.save(verificationToken);
        return token;
    }
}
