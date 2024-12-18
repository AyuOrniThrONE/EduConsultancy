package com.example.UserAuthModule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.UserAuthModule.entity.Role;
import com.example.UserAuthModule.repository.RoleRepository;

import jakarta.annotation.PostConstruct;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        // Create default roles if they don't exist
        if (roleRepository.findByName("ROLE_ADMIN") == null) {
            roleRepository.save(new Role("ROLE_ADMIN"));
        }
        if (roleRepository.findByName("ROLE_STUDENT") == null) {
            roleRepository.save(new Role("ROLE_STUDENT"));
        }
        if (roleRepository.findByName("ROLE_PARENT") == null) {
            roleRepository.save(new Role("ROLE_PARENT"));
        }
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}

