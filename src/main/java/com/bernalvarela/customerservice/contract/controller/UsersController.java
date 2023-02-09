package com.bernalvarela.customerservice.contract.controller;

import com.bernalvarela.customerservice.application.service.UserService;
import com.bernalvarela.customerservice.contract.mapper.UserDtoMapper;
import com.bernalvarela.customerservice.openapi.api.UsersApi;
import com.bernalvarela.customerservice.openapi.model.User;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UsersController implements UsersApi {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDtoMapper userDtoMapper;

    @Override
    public ResponseEntity<String> addUser(@Valid User user) {
        User createdUser = userDtoMapper.domainToDto(userService.createUser(userDtoMapper.dtoToDomain(user)));
        return ResponseEntity.ok("User with id " + createdUser.getId() + " is added");
    }

    @Override
    public ResponseEntity<User> getUser(
            @Parameter(name = "id", description = "", required = true) @PathVariable("id") Long id
    ) {
        return userService.getUser(id)
                .map(user -> ResponseEntity.ok(userDtoMapper.domainToDto(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userDtoMapper.domainToDto(userService.getUsers()));
    }

    @Override
    public ResponseEntity<Void> deleteUser(
            @Parameter(name = "id", description = "", required = true) @PathVariable("id") Long id
    ) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateUser(
            @Parameter(name = "id", description = "", required = true) @PathVariable("id") Long id,
            @Parameter(name = "User", description = "Update a user", required = true) @Valid @RequestBody User user
    ) {
        userService.updateUser(id, userDtoMapper.dtoToDomain(user));
        return new ResponseEntity<>(HttpStatus.OK);

    }
}