package com.bernalvarela.customerservice.infrastructure.repository;

import com.bernalvarela.customerservice.domain.exception.ElementNotFoundException;
import com.bernalvarela.customerservice.domain.model.Customer;
import com.bernalvarela.customerservice.domain.repository.CustomerRepository;
import com.bernalvarela.customerservice.infrastructure.mapper.CustomerEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerRepositoryJPA repository;

    private final CustomerEntityMapper mapper;

    public Customer save(Customer c) {
        return mapper.entityToDomain(repository.save(mapper.domainToEntity(c)));
    }

    public void update(Long id, Customer c) {
        if (repository.findById(id).isPresent()) {
            com.bernalvarela.customerservice.infrastructure.entity.Customer customer = mapper.domainToEntity(c);
            customer.setId(id);
            repository.save(customer);
        } else {
            throw new ElementNotFoundException();
        }
    }

    public List<Customer> findAll() {
        return mapper.entityToDomain(repository.findAll());
    }

    @Override public Customer findById(Long id) {
        return mapper.entityToDomain(repository.findById(id).orElseThrow(ElementNotFoundException::new));
    }

    @Override public void deleteById(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
        } else {
            throw new ElementNotFoundException();
        }
    }

}