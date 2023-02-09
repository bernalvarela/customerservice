package com.bernalvarela.customerservice.application.service;

import com.bernalvarela.customerservice.domain.model.User;
import com.bernalvarela.customerservice.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository repository;

    public User createUser(User u) {
        return repository.save(u);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public Optional<User> getUser(Long id) {
        return repository.findById(id);
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    public void updateUser(Long id, User u) {
        repository.update(id, u);
    }
}