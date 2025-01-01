package com.pol.user_service.auth.config;

import com.pol.user_service.auth.model.User;
import com.pol.user_service.auth.model.UserRole;
import com.pol.user_service.auth.model.UserRoleEnum;
import com.pol.user_service.auth.repository.UserRepository;
import com.pol.user_service.auth.repository.UserRoleRepository;
import com.pol.user_service.auth.service.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Configuration
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private final UserRepository userRepository;
    public DataInitializer(PasswordEncoder passwordEncoder, UserRoleService userRoleService, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
        this.userRepository = userRepository;
    }

    @Bean
    CommandLineRunner initRoles(UserRoleRepository roleRepository) {
        return args -> {
            List<String> roles = Arrays.stream(UserRoleEnum.values())
                    .map(Enum::name)
                    .toList();
            for (String roleName : roles) {
                if (roleRepository.findByRoleName(roleName).isEmpty()) {
                    roleRepository.save(UserRole
                            .builder()
                                    .roleName(roleName)
                            .build());
                }
            }

            String email = "admin@gmail.com";
            Set<UserRole> adminRoles = Collections.singleton(userRoleService.getAdminRole());
            if(!userRepository.existsByEmail(email)){
                User adminUser = User.builder()
                        .email(email)
                        .password(passwordEncoder.encode("12345678"))
                        .roles(adminRoles)
                        .firstName("ADMIN")
                        .lastName("LASTNAME OF ADMIN")
                        .username("admin007")
                        .build();

                userRepository.save(adminUser);
            }
        };
    }
}
