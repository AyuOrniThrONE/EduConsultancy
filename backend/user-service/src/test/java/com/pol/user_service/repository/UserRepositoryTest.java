package com.pol.user_service.repository;

import com.pol.user_service.auth.model.User;
import com.pol.user_service.auth.repository.UserRepository;
import com.pol.user_service.dto.user.UserDetailsDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;


import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @DisplayName("Unit test for findByEmail operation")
    @Test
    void givenUserObject_whenFindByEmail_thenReturnUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("john.doe@example.com");
        entityManager.persist(user);
        entityManager.flush();

        Optional<User> foundUser = userRepository.findByEmail("john.doe@example.com");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void givenEmail_whenGetUserDetailsByEmail_thenReturnUserDetailsDTO() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName("Jane");
        user.setLastName("Smith");
        user.setUsername("janesmith");
        user.setEmail("jane.smith@example.com");
        entityManager.persist(user);
        entityManager.flush();

        Optional<UserDetailsDTO> userDetails = userRepository.getUserDetailsByEmail("jane.smith@example.com");

        assertThat(userDetails).isPresent();
        assertThat(userDetails.get().getFirstName()).isEqualTo("Jane");
        assertThat(userDetails.get().getLastName()).isEqualTo("Smith");
        assertThat(userDetails.get().getUsername()).isEqualTo("janesmith");
        assertThat(userDetails.get().getEmail()).isEqualTo("jane.smith@example.com");
    }

    @Test
    void givenUserId_whenGetUserDetailsById_thenReturnUserDetailsDTO() {
        User user = new User();
        UUID userId = UUID.randomUUID();
        user.setId(userId);
        user.setFirstName("Alice");
        user.setLastName("Brown");
        user.setUsername("alicebrown");
        user.setEmail("alice.brown@example.com");
        entityManager.persist(user);
        entityManager.flush();

        Optional<UserDetailsDTO> userDetails = userRepository.getUserDetailsById(userId);

        assertThat(userDetails).isPresent();
        assertThat(userDetails.get().getFirstName()).isEqualTo("Alice");
        assertThat(userDetails.get().getLastName()).isEqualTo("Brown");
        assertThat(userDetails.get().getUsername()).isEqualTo("alicebrown");
        assertThat(userDetails.get().getEmail()).isEqualTo("alice.brown@example.com");
    }

    @Test
    void givenEmail_whenExistsByEmail_thenReturnTrue() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("existing.email@example.com");
        user.setUsername("user123");
        entityManager.persist(user);
        entityManager.flush();

        boolean exists = userRepository.existsByEmail("existing.email@example.com");

        assertThat(exists).isTrue();
    }

    @Test
    void givenUsername_whenExistsByUsername_thenReturnTrue() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("user@example.com");
        user.setUsername("uniqueUsername");
        entityManager.persist(user);
        entityManager.flush();

        boolean exists = userRepository.existsByUsername("uniqueUsername");

        assertThat(exists).isTrue();
    }

    @Test
    void givenUsername_whenExistsByUsername_thenReturnFalse() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("user@example.com");
        user.setUsername("uniqueUsername");
        entityManager.persist(user);
        entityManager.flush();

        boolean exists = userRepository.existsByUsername("wrongusername");

        assertThat(exists).isFalse();
    }

    @Test
    void givenDuplicateEmail_whenSaveUser_thenThrowException() {
        User user1 = new User();
        user1.setId(UUID.randomUUID());
        user1.setEmail("duplicate@example.com");
        user1.setUsername("user1");
        entityManager.persist(user1);
        entityManager.flush();

        User user2 = new User();
        user2.setId(UUID.randomUUID());
        user2.setEmail("duplicate@example.com");
        user2.setUsername("user2");

        assertThatThrownBy(() -> {
            entityManager.persist(user2);
            entityManager.flush();
        }).isInstanceOf(PersistenceException.class);
    }

    @Test
    void givenDuplicateUsername_whenSaveUser_thenThrowException() {
        User user1 = new User();
        user1.setId(UUID.randomUUID());
        user1.setEmail("user1@example.com");
        user1.setUsername("duplicateUsername");
        entityManager.persist(user1);
        entityManager.flush();

        User user2 = new User();
        user2.setId(UUID.randomUUID());
        user2.setEmail("user2@example.com");
        user2.setUsername("duplicateUsername");

        assertThatThrownBy(() -> {
            entityManager.persist(user2);
            entityManager.flush();
        }).isInstanceOf(PersistenceException.class);
    }

}

