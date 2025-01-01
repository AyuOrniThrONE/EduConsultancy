package com.pol.user_service.repository;

import com.pol.user_service.auth.model.ForgotPassword;
import com.pol.user_service.auth.model.User;
import com.pol.user_service.auth.repository.ForgotPasswordRepository;
import com.pol.user_service.auth.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class ForgotPasswordRepositoryTest {

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Given OTP and User exist when findByOtpAndUser then return ForgotPassword")
    void givenOtpAndUserExist_whenFindByOtpAndUser_thenReturnForgotPassword() {
        // Given
        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .username("johndoe")
                .password("password12")
                .email("john.doe@example.com")
                .build();
        userRepository.saveAndFlush(user);

        ForgotPassword forgotPassword = ForgotPassword.builder()
                .id(UUID.randomUUID())
                .otp(123456)
                .expirationTime(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hour later
                .attempts(0)
                .user(user)
                .build();
        forgotPasswordRepository.saveAndFlush(forgotPassword);

        // When
        Optional<ForgotPassword> found = forgotPasswordRepository.findByOtpAndUser(123456, user);

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getOtp()).isEqualTo(123456);
        assertThat(found.get().getUser().getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    @DisplayName("Given OTP does not exist when findByOtpAndUser then return empty")
    void givenOtpDoesNotExist_whenFindByOtpAndUser_thenReturnEmpty() {
        // Given
        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .build();
        userRepository.saveAndFlush(user);

        // When
        Optional<ForgotPassword> found = forgotPasswordRepository.findByOtpAndUser(999999, user);

        // Then
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Given user does not exist when findByOtpAndUser then throw exception")
    void givenUserDoesNotExist_whenFindByOtpAndUser_thenThrowException() {
        // Given
        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("NonExistent")
                .lastName("User")
                .email("non.existent@example.com")
                .build();

        // When / Then
        assertThatThrownBy(() -> forgotPasswordRepository.findByOtpAndUser(123456, user))
                .isInstanceOf(InvalidDataAccessApiUsageException.class);
    }

    @Test
    @DisplayName("Given expired OTP when findByOtpAndUser then return empty")
    void givenExpiredOtp_whenFindByOtpAndUser_thenReturnEmpty() {
        // Given
        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Expired")
                .lastName("User")
                .email("expired.user@example.com")
                .build();
        userRepository.saveAndFlush(user);

        ForgotPassword forgotPassword = ForgotPassword.builder()
                .id(UUID.randomUUID())
                .otp(654321)
                .expirationTime(new Date(System.currentTimeMillis() - 3600 * 1000)) // 1 hour earlier
                .attempts(0)
                .user(user)
                .build();
        forgotPasswordRepository.saveAndFlush(forgotPassword);

        // When
        Optional<ForgotPassword> found = forgotPasswordRepository.findByOtpAndUser(654321, user);

        // Then
        assertThat(found).isEmpty();
    }
}
