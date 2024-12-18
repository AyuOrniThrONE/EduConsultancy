package com.example.UserAuthModule.repository;

import com.example.UserAuthModule.entity.Role;
import com.example.UserAuthModule.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}