package com.bernalvarela.customerservice.infrastructure.repository;

import com.bernalvarela.customerservice.infrastructure.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepositoryJPA extends JpaRepository<User, Long> {

    List<User> findByUsername(String username);

}