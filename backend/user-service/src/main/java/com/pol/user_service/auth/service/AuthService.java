package com.pol.user_service.auth.service;


import com.pol.user_service.auth.dto.AuthResponseDTO;
import com.pol.user_service.auth.dto.LoginRequestDTO;
import com.pol.user_service.auth.dto.RegisterRequestDTO;
import com.pol.user_service.auth.model.User;
import com.pol.user_service.auth.model.UserRole;
import com.pol.user_service.auth.repository.UserRepository;
import com.pol.user_service.client.EmailFeignClient;
//import com.pol.user_service.constants.KafkaTopics;
import com.pol.user_service.dto.feign.NewUserRequestDto;
import com.pol.user_service.exception.customExceptions.DatabaseAccessException;
import com.pol.user_service.exception.customExceptions.UserAlreadyExists;
//import com.pol.user_service.schema.avro.UserRegisteredEvent;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final JwtService jwtService;
    private final UserRoleService userRoleService;
    private final RefreshTokenService refreshTokenService;

    private final EmailFeignClient emailFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

//    public AuthService(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserRepository userRepository, JwtService jwtService, UserRoleService userRoleService, RefreshTokenService refreshTokenService, EmailFeignClient emailFeignClient) {
//        this.passwordEncoder = passwordEncoder;
//        this.authenticationManager = authenticationManager;
//        this.userRepository = userRepository;
//        this.jwtService = jwtService;
//        this.userRoleService = userRoleService;
//        this.refreshTokenService = refreshTokenService;
//        this.emailFeignClient = emailFeignClient;
//    }
//    private final KafkaTemplate<String, Object> kafkaTemplate;

    public AuthResponseDTO register(RegisterRequestDTO registerRequestDTO){
        if(userRepository.existsByEmail(registerRequestDTO.getEmail())){
            throw new UserAlreadyExists("User with email id "+registerRequestDTO.getEmail()+" already exists");
        }
        if(userRepository.existsByUsername(registerRequestDTO.getUsername())){
            throw new UserAlreadyExists("User with username "+registerRequestDTO.getUsername()+" already exists");
        }
        UserRole defaultRole = userRoleService.getDefaultRoleUser();
        var user = User.builder()
                .firstName(registerRequestDTO.getFirstName())
                .lastName(registerRequestDTO.getLastName())
                .email(registerRequestDTO.getEmail())
                .username(registerRequestDTO.getUsername())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .roles(Collections.singleton(defaultRole))
                .build();
        try {
            User savedUser = userRepository.save(user);
//            UserRegisteredEvent newUserRegisteredEvent = new UserRegisteredEvent();
//            newUserRegisteredEvent.setEmail("avvickya@gmail.com");
//            newUserRegisteredEvent.setName(savedUser.getUsername());

            NewUserRequestDto newUserRequestDto = new NewUserRequestDto();
            newUserRequestDto.setName(registerRequestDTO.getFirstName());
            newUserRequestDto.setEmail(registerRequestDTO.getEmail());
            try {
//                kafkaTemplate.send(KafkaTopics.UserRegisteredTopic, newUserRegisteredEvent);
                emailFeignClient.sendWelcomeEmail(newUserRequestDto);
            } catch (Exception kafkaException) {
                logger.error("Failed to publish user registration event to Kafka", kafkaException);
            }
            var accessToken = jwtService.generateToken(savedUser);
            var refreshToken = refreshTokenService.createRefreshToken(savedUser.getEmail());
            return AuthResponseDTO.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken.getRefreshToken())
                    .build();
        } catch (DataIntegrityViolationException e) {
            logger.error("User registration failed: Duplicate email detected", e);
            throw new UserAlreadyExists("A user with this email already exists.");
        } catch (ConstraintViolationException e) {
            // Handle validation constraint failure from db
            logger.error("User registration failed: Validation constraint violation", e);
            throw new RuntimeException("User data is invalid.");
        } catch (DataAccessException e) {
            // Handle general database access errors
            logger.error("User registration failed: Database access error", e);
            throw new DatabaseAccessException("Unable to save user due to database error.");
        }
    }

    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getPassword()
                )
        );

        User user = userRepository.findByEmail(loginRequestDTO
                        .getEmail())
                .orElseThrow(()->
                        new UsernameNotFoundException("User not found with email : " + loginRequestDTO.getEmail()));
        var accessToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(loginRequestDTO.getEmail());

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }
}
