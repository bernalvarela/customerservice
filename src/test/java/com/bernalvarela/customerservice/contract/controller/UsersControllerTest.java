package com.bernalvarela.customerservice.contract.controller;

import com.bernalvarela.customerservice.application.service.UserService;
import com.bernalvarela.customerservice.domain.exception.ElementAlreadyExistsException;
import com.bernalvarela.customerservice.domain.exception.ElementNotFoundException;
import com.bernalvarela.customerservice.infrastructure.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.bernalvarela.customerservice.util.JsonUtil.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    private static final String NAME = "name";

    private static final String SURNAME = "surname";

    private static final String USERNAME = "username";

    private static final boolean ADMIN = true;

    private static final Long ID = 1L;

    private static final User USER = User.builder().name(NAME).surname(SURNAME).username(USERNAME).admin(ADMIN).build();

    private static final com.bernalvarela.customerservice.domain.model.User USERMODEL =
            com.bernalvarela.customerservice.domain.model.User.builder()
                    .id(ID)
                    .name(NAME)
                    .surname(SURNAME)
                    .username(USERNAME)
                    .admin(ADMIN)
                    .build();

    @Test
    void shouldReturnCreatedUserWhenAddUser() throws Exception {
        when(service.createUser(any())).thenReturn(USERMODEL);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(asJsonString(USER))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID));
    }

    @Test
    void shouldReturnErrorWhenAddUserWithAnExistingUsername() throws Exception {
        when(service.createUser(any())).thenThrow(ElementAlreadyExistsException.class);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(asJsonString(USER))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturnUserWhenGetExistingUser() throws Exception {
        when(service.getUser(ID)).thenReturn(USERMODEL);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/{id}", ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID));
    }

    @Test
    void shouldReturnNotFoundWhenGetNonExistingUser() throws Exception {
        when(service.getUser(ID)).thenThrow(ElementNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/{id}", ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnUsersListWhenGetUsers() throws Exception {
        when(service.getUsers()).thenReturn(List.of(USERMODEL));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void shouldReturnNoContentWhenDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/users/{id}", ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundWhenNoExistDeleteUser() throws Exception {
        doThrow(new ElementNotFoundException()).when(service).deleteUser(any());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/users/{id}", ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnOkWhenUpdateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/users/{id}", ID)
                        .content(asJsonString(USER))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnNotFoundWhenNotExistingUpdateUser() throws Exception {
        doThrow(new ElementNotFoundException()).when(service).updateUser(any(), any());
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/users/{id}", ID)
                        .content(asJsonString(USER))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}