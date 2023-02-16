package com.bernalvarela.customerservice.infrastructure.repository;

import com.bernalvarela.customerservice.domain.exception.ElementNotFoundException;
import com.bernalvarela.customerservice.domain.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomerRepositoryImplTest {

    @Autowired
    private CustomerRepositoryImpl repository;

    @Autowired
    private CustomerRepositoryJPA jpaRepository;

    private static final String NAME = "name";

    private static final String NAME2 = "name2";

    private static final String SURNAME = "surname";

    private static final String SURNAME2 = "surname2";

    private static final String PHOTO = "photo";

    private static final String PHOTO2 = "photo2";

    private static final Long ID = 1L;

    private static final Customer CUSTOMERMODEL = Customer.builder()
            .name(NAME)
            .surname(SURNAME)
            .photo(PHOTO)
            .build();

    private static final com.bernalvarela.customerservice.infrastructure.entity.Customer CUSTOMER =
            com.bernalvarela.customerservice.infrastructure.entity.Customer.builder()
                    .name(NAME2)
                    .surname(SURNAME2)
                    .photo(PHOTO2)
                    .build();

    @Test
    void shouldSaveCustomer() {
        Customer savedCustomer = repository.save(CUSTOMERMODEL);
        assertNull(CUSTOMERMODEL.getId());
        assertNotNull(savedCustomer.getId());
        assertEquals(CUSTOMERMODEL.getName(), savedCustomer.getName());
        assertEquals(CUSTOMERMODEL.getSurname(), savedCustomer.getSurname());
        assertEquals(CUSTOMERMODEL.getPhoto(), savedCustomer.getPhoto());
        jpaRepository.deleteById(savedCustomer.getId());
    }

    @Test
    void shouldReturnAllCustomers() {
        Customer c1 = repository.save(CUSTOMERMODEL);
        Customer c2 = repository.save(CUSTOMERMODEL);
        Customer c3 = repository.save(CUSTOMERMODEL);

        List<Customer> customers = repository.findAll();
        assertEquals(3, customers.size());
        jpaRepository.deleteById(c1.getId());
        jpaRepository.deleteById(c2.getId());
        jpaRepository.deleteById(c3.getId());
    }

    @Test
    void shouldFindACustomer() {
        Customer c1 = repository.save(CUSTOMERMODEL);

        Customer recoveredCustomer = repository.findById(c1.getId());
        assertEquals(c1.getId(), recoveredCustomer.getId());
        assertEquals(c1.getName(), recoveredCustomer.getName());
        assertEquals(c1.getSurname(), recoveredCustomer.getSurname());
        assertEquals(c1.getPhoto(), recoveredCustomer.getPhoto());

        jpaRepository.deleteById(c1.getId());
    }

    @Test
    void shouldFailWhenFindACustomerUnexistent() {
        try {
            jpaRepository.deleteById(ID);
        } catch (EmptyResultDataAccessException ignored) {
        }

        assertThrows(ElementNotFoundException.class, () -> {
            repository.findById(ID);
        });
    }

    @Test
    void shouldDeleteACustomer() {
        com.bernalvarela.customerservice.infrastructure.entity.Customer c1 = jpaRepository.save(CUSTOMER);

        repository.deleteById(c1.getId());

        assertEquals(jpaRepository.findById(c1.getId()), Optional.empty());
    }

    @Test
    void shouldFailWhenDeleteACustomerUnexistent() {
        try {
            jpaRepository.deleteById(ID);
        } catch (EmptyResultDataAccessException ignored) {
        }

        assertThrows(ElementNotFoundException.class, () -> {
            repository.deleteById(ID);
        });
    }
}
