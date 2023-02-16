package com.bernalvarela.customerservice.contract.controller;

import com.bernalvarela.customerservice.application.service.UserService;
import com.bernalvarela.customerservice.contract.mapper.UserDtoMapper;
import com.bernalvarela.customerservice.domain.exception.ElementAlreadyExistsException;
import com.bernalvarela.customerservice.domain.exception.ElementNotFoundException;
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
    private UserService service;

    @Autowired
    private UserDtoMapper mapper;

    @Override
    public ResponseEntity<User> addUser(@Valid User user) {
        try {
            User createdUser = mapper.domainToDto(service.createUser(mapper.dtoToVO(user)));
            return ResponseEntity.ok(createdUser);
        } catch (ElementAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @Override
    public ResponseEntity<User> getUser(
            @Parameter(name = "id", description = "", required = true) @PathVariable("id") Long id
    ) {
        try {
            return ResponseEntity.ok(mapper.domainToDto(service.getUser(id)));
        } catch (ElementNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(mapper.domainToDto(service.getUsers()));
    }

    @Override
    public ResponseEntity<Void> deleteUser(
            @Parameter(name = "id", description = "", required = true) @PathVariable("id") Long id
    ) {
        try {
            service.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ElementNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> updateUser(
            @Parameter(name = "id", description = "", required = true) @PathVariable("id") Long id,
            @Parameter(name = "User", description = "Update a user", required = true) @Valid @RequestBody User user
    ) {
        try {
            service.updateUser(id, mapper.dtoToVO(user));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> changeAdminStatus(
            @Parameter(name = "id", description = "", required = true) @PathVariable("id") Long id,
            @Parameter(name = "User", description = "Update a user", required = true) @Valid @RequestBody User user
    ) {
        try {
            service.updateAdninStatus(id, user.getAdmin());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}