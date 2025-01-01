package com.pol.user_service.repository;

import com.pol.user_service.auth.model.UserRole;
import com.pol.user_service.auth.repository.UserRoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class UserRoleRepositoryTest {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void givenRoleName_whenFindByRoleName_thenReturnUserRole() {
        UserRole userRole = UserRole.builder()
                .id(UUID.randomUUID())
                .roleName("ADMIN")
                .build();
        entityManager.persist(userRole);
        entityManager.flush();

        Optional<UserRole> foundRole = userRoleRepository.findByRoleName("ADMIN");

        assertThat(foundRole).isPresent();
        assertThat(foundRole.get().getRoleName()).isEqualTo("ADMIN");
    }

    @Test
    void givenRoleName_whenFindByRoleName_thenReturnEmptyOptional() {
        Optional<UserRole> foundRole = userRoleRepository.findByRoleName("NON_EXISTENT_ROLE");

        assertThat(foundRole).isEmpty();
    }

    @Test
    void givenDuplicateRoleName_whenSaveUserRole_thenThrowException() {
        UserRole role1 = UserRole.builder()
                .id(UUID.randomUUID())
                .roleName("STUDENT")
                .build();

        UserRole role2 = UserRole.builder()
                .id(UUID.randomUUID())
                .roleName("STUDENT")
                .build();

        entityManager.persist(role1);
        entityManager.flush();

        assertThatThrownBy(() -> {
            entityManager.persist(role2);
            entityManager.flush();
        }).isInstanceOf(PersistenceException.class);
    }

    @Test
    void givenMultipleRoles_whenFindByRoleName_thenReturnCorrectRole() {
        UserRole adminRole = UserRole.builder()
                .id(UUID.randomUUID())
                .roleName("ADMIN")
                .build();

        UserRole studentRole = UserRole.builder()
                .id(UUID.randomUUID())
                .roleName("STUDENT")
                .build();

        entityManager.persist(adminRole);
        entityManager.persist(studentRole);
        entityManager.flush();

        Optional<UserRole> foundRole = userRoleRepository.findByRoleName("STUDENT");

        assertThat(foundRole).isPresent();
        assertThat(foundRole.get().getRoleName()).isEqualTo("STUDENT");
    }
}
