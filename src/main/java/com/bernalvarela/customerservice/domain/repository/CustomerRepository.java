package com.bernalvarela.customerservice.domain.repository;

import com.bernalvarela.customerservice.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}