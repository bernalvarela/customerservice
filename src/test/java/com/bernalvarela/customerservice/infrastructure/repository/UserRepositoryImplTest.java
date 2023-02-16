package com.bernalvarela.customerservice.infrastructure.repository;

import com.bernalvarela.customerservice.domain.exception.ElementAlreadyExistsException;
import com.bernalvarela.customerservice.domain.exception.ElementNotFoundException;
import com.bernalvarela.customerservice.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryImplTest {

    @Autowired
    private UserRepositoryImpl repository;

    @Autowired
    private UserRepositoryJPA jpaRepository;

    private static final String NAME = "name";

    private static final String NAME2 = "name2";

    private static final String SURNAME = "surname";

    private static final String SURNAME2 = "surname2";

    private static final String USERNAME = "username";

    private static final String USERNAME2 = "username2";

    private static final String USERNAME3 = "username3";

    private static final boolean ADMIN = true;

    private static final boolean ADMIN2 = false;

    private static final Long ID = 1L;

    private static final User USERMODEL = User.builder()
            .name(NAME)
            .surname(SURNAME)
            .username(USERNAME)
            .admin(ADMIN)
            .build();

    private static final com.bernalvarela.customerservice.infrastructure.entity.User USER =
            com.bernalvarela.customerservice.infrastructure.entity.User.builder()
                    .name(NAME2)
                    .surname(SURNAME2)
                    .username(USERNAME2)
                    .admin(ADMIN2)
                    .build();

    @Test
    void shouldSaveUser() {
        User savedUser = repository.save(USERMODEL);
        assertNull(USERMODEL.getId());
        assertNotNull(savedUser.getId());
        assertEquals(USERMODEL.getName(), savedUser.getName());
        assertEquals(USERMODEL.getSurname(), savedUser.getSurname());
        assertEquals(USERMODEL.getUsername(), savedUser.getUsername());
        assertEquals(USERMODEL.getAdmin(), savedUser.getAdmin());
        jpaRepository.deleteById(savedUser.getId());
    }

    @Test
    void shouldReturnErrorWhenSaveUserWithExistentUsername() {
        User savedUser = repository.save(USERMODEL);

        assertThrows(ElementAlreadyExistsException.class, () -> {
            repository.save(USERMODEL);
        });

        jpaRepository.deleteById(savedUser.getId());
    }

    @Test
    void shouldReturnAllUsers() {
        User u1 = repository.save(USERMODEL);
        User u2 = repository.save(USERMODEL.toBuilder().username(USERNAME2).build());
        User u3 = repository.save(USERMODEL.toBuilder().username(USERNAME3).build());

        List<User> customers = repository.findAll();
        assertEquals(3, customers.size());
        jpaRepository.deleteById(u1.getId());
        jpaRepository.deleteById(u2.getId());
        jpaRepository.deleteById(u3.getId());
    }

    @Test
    void shouldFindAUser() {
        User user = repository.save(USERMODEL);

        User recoveredUser = repository.findById(user.getId());
        assertEquals(user.getId(), recoveredUser.getId());
        assertEquals(user.getName(), recoveredUser.getName());
        assertEquals(user.getSurname(), recoveredUser.getSurname());
        assertEquals(user.getAdmin(), recoveredUser.getAdmin());

        jpaRepository.deleteById(user.getId());
    }

    @Test
    void shouldFailWhenFindAUserUnexistent() {
        try {
            jpaRepository.deleteById(ID);
        } catch (EmptyResultDataAccessException ignored) {
        }

        assertThrows(ElementNotFoundException.class, () -> {
            repository.findById(ID);
        });
    }

    @Test
    void shouldDeleteAUser() {
        com.bernalvarela.customerservice.infrastructure.entity.User u1 = jpaRepository.save(USER);

        repository.deleteById(u1.getId());

        assertEquals(jpaRepository.findById(u1.getId()), Optional.empty());
    }

    @Test
    void shouldFailWhenDeleteAUserUnexistent() {
        try {
            jpaRepository.deleteById(ID);
        } catch (EmptyResultDataAccessException ignored) {
        }

        assertThrows(ElementNotFoundException.class, () -> {
            repository.deleteById(ID);
        });
    }

    @Test
    void shouldUpdateUserAdminStatus() {
        com.bernalvarela.customerservice.infrastructure.entity.User u1 = jpaRepository.save(USER);

        repository.updateAdminStatus(u1.getId(), !u1.getAdmin());

        jpaRepository.findById(u1.getId()).ifPresent(user -> assertEquals(user.getAdmin(), !u1.getAdmin()));

        jpaRepository.deleteById(u1.getId());
    }

    @Test
    void shouldUpdateUserAdminStatusUserUnexistent() {
        try {
            jpaRepository.deleteById(ID);
        } catch (EmptyResultDataAccessException ignored) {
        }

        assertThrows(ElementNotFoundException.class, () -> {
            repository.updateAdminStatus(ID, true);
        });
    }
}
