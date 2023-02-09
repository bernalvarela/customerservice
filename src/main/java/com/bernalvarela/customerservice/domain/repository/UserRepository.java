package com.bernalvarela.customerservice.domain.repository;

import com.bernalvarela.customerservice.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User u);

    List<User> findAll();

    User findById(Long id);

    void deleteById(Long id);

    void update(Long id, User u);
}