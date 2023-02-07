package com.bernalvarela.customerservice.application.service;

import com.bernalvarela.customerservice.domain.entity.Customer;
import com.bernalvarela.customerservice.domain.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CustomerService {

    private CustomerRepository repository;

    public List<Customer> getCustomers() {
        return (List<Customer>) repository.findAll();
    }
}
