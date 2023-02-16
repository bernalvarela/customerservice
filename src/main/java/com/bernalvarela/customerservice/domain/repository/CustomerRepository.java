package com.bernalvarela.customerservice.domain.repository;

import com.bernalvarela.customerservice.domain.model.Customer;

import java.util.List;

public interface CustomerRepository {

    Customer save(Customer c);

    List<Customer> findAll();

    Customer findById(Long id);

    void deleteById(Long id);

}