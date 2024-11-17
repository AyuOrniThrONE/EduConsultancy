package com.example.UserAuthModule.service;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.UserAuthModule.config.SignupRequest;
import com.example.UserAuthModule.entity.ERole;
import com.example.UserAuthModule.entity.Role;
import com.example.UserAuthModule.entity.User;
import com.example.UserAuthModule.entity.VerificationToken;
import com.example.UserAuthModule.repository.RoleRepository;
import com.example.UserAuthModule.repository.UserRepository;
import com.example.UserAuthModule.repository.VerificationTokenRepository;

@Service
public class UserService {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
    
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
    public User registerUser(SignupRequest request) {
    	if(userRepository.existsByUsername(request.getUsername())) {
    		throw new RuntimeException("Username is already taken");
    	}
    	if(userRepository.existsByEmail(request.getEmail())) {
    		throw new RuntimeException("Email is already in use.");
    	}
    	User user=new User();
    	user.setUsername(request.getUsername());
    	user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role role=roleRepository.findByName(ERole.ROLE_USER.toString()).orElseThrow(()->new RuntimeException("Role not found"));
        user.setRole(role);
        return userRepository.save(user);

    }

}
