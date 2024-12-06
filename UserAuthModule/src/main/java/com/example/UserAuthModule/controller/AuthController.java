package com.example.UserAuthModule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.UserAuthModule.entity.User;
import com.example.UserAuthModule.entity.VerificationToken;
import com.example.UserAuthModule.repository.UserRepository;
import com.example.UserAuthModule.repository.VerificationTokenRepository;
import com.example.UserAuthModule.service.EmailService;
import com.example.UserAuthModule.service.UserService;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private VerificationTokenRepository tokenRepository;
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        User registeredUser = userService.registerNewUser(user);
        String token = userService.generateVerificationToken(registeredUser);
        emailService.sendVerificationEmail(registeredUser.getEmail(), token);
        return "Registration successful. Please check your email to verify your account.";
    }

    @GetMapping("/verify")
    public String verifyAccount(@RequestParam("token") String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return "Invalid verification token";
        }
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        return "Account verified successfully";
    }
}
