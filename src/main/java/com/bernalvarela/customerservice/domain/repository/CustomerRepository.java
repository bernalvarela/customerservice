package com.bernalvarela.customerservice.domain.repository;

import com.bernalvarela.customerservice.domain.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    Customer save(Customer c);

    List<Customer> findAll();

    Customer findById(Long id);

    void deleteById(Long id);

    void update(Long id, Customer c);
}