package com.pol.user_service.repository;

import com.pol.user_service.auth.model.RefreshToken;
import com.pol.user_service.auth.model.User;
import com.pol.user_service.auth.repository.RefreshTokenRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class RefreshTokenRepositoryTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void givenRefreshToken_whenFindByRefreshToken_thenReturnRefreshToken() {
        // Arrange
        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .username("johndoe")
                .build();

        entityManager.persist(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .id(UUID.randomUUID())
                .refreshToken("sample-refresh-token")
                .expirationTime(Instant.now().plusSeconds(3600))
                .user(user)
                .build();

        entityManager.persist(refreshToken);
        entityManager.flush();

        Optional<RefreshToken> foundToken = refreshTokenRepository.findByRefreshToken("sample-refresh-token");

        assertThat(foundToken).isPresent();
        assertThat(foundToken.get().getRefreshToken()).isEqualTo("sample-refresh-token");
        assertThat(foundToken.get().getUser().getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void givenNonExistentRefreshToken_whenFindByRefreshToken_thenReturnEmptyOptional() {
        Optional<RefreshToken> foundToken = refreshTokenRepository.findByRefreshToken("non-existent-token");

        assertThat(foundToken).isEmpty();
    }

    @Test
    void givenRefreshToken_whenSave_thenPersistTokenSuccessfully() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Alice")
                .lastName("Smith")
                .email("alice.smith@example.com")
                .username("alicesmith")
                .build();

        entityManager.persist(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .id(UUID.randomUUID())
                .refreshToken("new-refresh-token")
                .expirationTime(Instant.now().plusSeconds(7200))
                .user(user)
                .build();

        RefreshToken savedToken = refreshTokenRepository.save(refreshToken);

        assertThat(savedToken.getId()).isNotNull();
        assertThat(savedToken.getRefreshToken()).isEqualTo("new-refresh-token");
        assertThat(savedToken.getExpirationTime()).isNotNull();
        assertThat(savedToken.getUser()).isEqualTo(user);
    }

    @Test
    void givenRefreshTokenWithoutUser_whenSave_thenThrowException() {
        RefreshToken refreshToken = RefreshToken.builder()
                .id(UUID.randomUUID())
                .refreshToken("orphan-token")
                .expirationTime(Instant.now().plusSeconds(3600))
                .build();

        assertThatThrownBy(() -> {
            refreshTokenRepository.saveAndFlush(refreshToken);
        }).isInstanceOf(Exception.class); // This will likely be a ConstraintViolationException or PersistenceException
    }

    @Test
    void givenExpiredRefreshToken_whenFindByRefreshToken_thenReturnToken() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Bob")
                .lastName("Brown")
                .email("bob.brown@example.com")
                .username("bobbrown")
                .build();

        entityManager.persist(user);

        RefreshToken expiredToken = RefreshToken.builder()
                .id(UUID.randomUUID())
                .refreshToken("expired-token")
                .expirationTime(Instant.now().minusSeconds(3600)) // Set expiration time in the past
                .user(user)
                .build();

        entityManager.persist(expiredToken);
        entityManager.flush();

        Optional<RefreshToken> foundToken = refreshTokenRepository.findByRefreshToken("expired-token");

        assertThat(foundToken).isPresent();
        assertThat(foundToken.get().getExpirationTime()).isBefore(Instant.now());
    }
}
