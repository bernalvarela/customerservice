package com.bernalvarela.customerservice.infrastructure.repository;

import com.bernalvarela.customerservice.domain.exception.ElementAlreadyExistsException;
import com.bernalvarela.customerservice.domain.exception.ElementNotFoundException;
import com.bernalvarela.customerservice.domain.model.User;
import com.bernalvarela.customerservice.domain.repository.UserRepository;
import com.bernalvarela.customerservice.infrastructure.mapper.UserEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserRepositoryJPA repository;

    private final UserEntityMapper mapper;

    public User save(User u) {
        List<User> users = mapper.entityToDomain(repository.findByUsername(u.getUsername()));
        User createdUser = null;
        if (users == null || users.isEmpty()) {
            createdUser = mapper.entityToDomain(repository.save(mapper.domainToEntity(u)));
        } else {
            throw new ElementAlreadyExistsException();
        }
        return createdUser;
    }

    public void update(Long id, User u) {
        if (repository.findById(id).isPresent()) {
            com.bernalvarela.customerservice.infrastructure.entity.User user = mapper.domainToEntity(u);
            user.setId(id);
            repository.save(user);
        } else {
            throw new ElementNotFoundException();
        }
    }

    public List<User> findAll() {
        return mapper.entityToDomain(repository.findAll());
    }

    @Override public User findById(Long id) {
        return mapper.entityToDomain(repository.findById(id).orElseThrow(ElementNotFoundException::new));
    }

    @Override public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ElementNotFoundException();
        }
    }

}