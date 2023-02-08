package com.bernalvarela.customerservice.infrastructure.repository;

import com.bernalvarela.customerservice.infrastructure.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepositoryJPA extends JpaRepository<Customer, Long> {
}