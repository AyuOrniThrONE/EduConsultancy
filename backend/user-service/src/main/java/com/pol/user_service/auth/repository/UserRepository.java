package com.pol.user_service.auth.repository;

import com.pol.user_service.auth.model.User;
import com.pol.user_service.dto.user.UserDetailsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    @Query("SELECT new com.pol.user_service.dto.user.UserDetailsDTO(u.firstName, u.lastName, u.username, u.email) " +
            "FROM User u WHERE u.email = :email")
    Optional<UserDetailsDTO> getUserDetailsByEmail(@Param("email") String email);

    @Query("SELECT new com.pol.user_service.dto.user.UserDetailsDTO(u.firstName, u.lastName, u.username, u.email) " +
            "FROM User u WHERE u.id = :id")
    Optional<UserDetailsDTO> getUserDetailsById(@Param("id") UUID id);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
