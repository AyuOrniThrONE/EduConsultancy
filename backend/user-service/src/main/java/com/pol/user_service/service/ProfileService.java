package com.pol.user_service.service;

import com.pol.user_service.auth.repository.UserRepository;
import com.pol.user_service.dto.user.UserDetailsDTO;
import com.pol.user_service.exception.customExceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileService {
    private final UserRepository userRepository;
    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetailsDTO getUserDetails(String userId){
        return userRepository.getUserDetailsById(UUID.fromString(userId)).orElseThrow(()->new UserNotFoundException("User not found . Try login"));
    }
}
