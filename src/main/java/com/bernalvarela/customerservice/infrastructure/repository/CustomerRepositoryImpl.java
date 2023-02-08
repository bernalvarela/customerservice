package com.bernalvarela.customerservice.infrastructure.repository;

import com.bernalvarela.customerservice.domain.model.Customer;
import com.bernalvarela.customerservice.domain.repository.CustomerRepository;
import com.bernalvarela.customerservice.infrastructure.mapper.CustomerEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerRepositoryJPA customerRepositoryJPA;

    private final CustomerEntityMapper mapper;

    public void update(Long id, Customer c) {
        if (customerRepositoryJPA.findById(id).isPresent()) {
            com.bernalvarela.customerservice.infrastructure.entity.Customer customer = mapper.domainToEntity(c);
            customer.setId(id);
            customerRepositoryJPA.save(customer);
        }
    }

    public List<Customer> findAll() {
        return mapper.entityToDomain(customerRepositoryJPA.findAll());
    }

    @Override public Optional<Customer> findById(Long id) {
        return Optional.empty();
    }

    @Override public void deleteById(Long id) {
        customerRepositoryJPA.deleteById(id);
    }

}