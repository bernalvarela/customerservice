package com.bernalvarela.customerservice.application.service;

import com.bernalvarela.customerservice.domain.model.Customer;
import com.bernalvarela.customerservice.domain.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomerService {

    private CustomerRepository repository;

    public List<Customer> getCustomers() {
        return repository.findAll();
    }

    public Optional<Customer> getCustomer(Long id) {
        return repository.findById(id);
    }

    public void deleteCustomer(Long id) {
        repository.deleteById(id);
    }

    public void updateCustomer(Long id, Customer c) {
        repository.update(id, c);
    }
}
