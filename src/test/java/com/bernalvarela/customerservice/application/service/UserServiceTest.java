package com.bernalvarela.customerservice.application.service;

import com.bernalvarela.customerservice.application.mapper.UserVOMapper;
import com.bernalvarela.customerservice.application.vo.UserVO;
import com.bernalvarela.customerservice.domain.model.User;
import com.bernalvarela.customerservice.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private static UserRepository repository;

    @Mock
    private static UserVOMapper mapper;

    private static UserService service;

    private static final String NAME = "name";

    private static final String SURNAME = "surname";

    private static final String USERNAME = "username";

    private static final boolean ADMIN = true;

    private static final Long ID = 1L;

    private static final User USER_MODEL = User.builder()
            .id(ID)
            .name(NAME)
            .surname(SURNAME)
            .username(USERNAME)
            .admin(ADMIN)
            .build();

    private static final UserVO USER_VO = UserVO.builder()
            .id(ID)
            .name(NAME)
            .surname(SURNAME)
            .username(USERNAME)
            .admin(ADMIN)
            .build();

    @BeforeEach
    public void init() {
        service = new UserService(repository, mapper);
    }

    @Test
    void shouldCallSaveInRepositoryWhenCreateUser() {
        service.createUser(USER_VO);
        verify(repository, times(1)).save(any());
    }

    @Test
    void shouldCallFindAllWhenGetUsers() {
        service.getUsers();
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldCallFindByIdWhenGetUser() {
        service.getUser(ID);
        verify(repository, times(1)).findById(ID);
    }

    @Test
    void shouldCallDeleteByIdWhenDeleteUser() {
        service.deleteUser(ID);
        verify(repository, times(1)).deleteById(ID);
    }

    @Test
    void shouldCallUpdateWhenUpdateUser() {
        when(repository.findById(any())).thenReturn(USER_MODEL);
        service.updateUser(ID, USER_VO);
        verify(repository, times(1)).save(any());
    }

    @Test
    void shouldCallUpdateAdminStatusWhenUpdateAdminUser() {
        service.updateAdninStatus(ID, ADMIN);
        verify(repository, times(1)).updateAdminStatus(ID, ADMIN);
    }
}
