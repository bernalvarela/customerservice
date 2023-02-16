package com.bernalvarela.customerservice.application.service;

import com.bernalvarela.customerservice.application.mapper.UserVOMapper;
import com.bernalvarela.customerservice.application.vo.UserVO;
import com.bernalvarela.customerservice.domain.model.User;
import com.bernalvarela.customerservice.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository repository;

    private UserVOMapper mapper;

    public User createUser(UserVO u) {
        return repository.save(mapper.voToDomain(u));
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User getUser(Long id) {
        return repository.findById(id);
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    public void updateUser(Long id, UserVO u) {
        User recoveredUser = repository.findById(id);
        u.setId(recoveredUser.getId());
        repository.save(mapper.voToDomain(u));
    }

    public void updateAdninStatus(Long id, Boolean admin) {
        repository.updateAdminStatus(id, admin);
    }
}
