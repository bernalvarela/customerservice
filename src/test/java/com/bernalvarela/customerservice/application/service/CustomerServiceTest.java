package com.bernalvarela.customerservice.application.service;

import com.bernalvarela.customerservice.domain.model.Customer;
import com.bernalvarela.customerservice.domain.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private static CustomerRepository repository;

    private static CustomerService service;

    private static final String NAME = "name";

    private static final String SURNAME = "surname";

    private static final String PHOTO = "photo";

    private static final Long ID = 1L;

    private static final Customer CUSTOMERMODEL = Customer.builder()
            .id(ID)
            .name(NAME)
            .surname(SURNAME)
            .photo(PHOTO)
            .build();

    @BeforeEach
    public void init() {
        service = new CustomerService(repository);
    }

    @Test
    void shouldCallSaveInRepositoryWhenCreateCustomer() {
        service.createCustomer(CUSTOMERMODEL);
        verify(repository, times(1)).save(CUSTOMERMODEL);
    }

    @Test
    void shouldCallFindAllWhenGetCustomers() {
        service.getCustomers();
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldCallFindByIdWhenGetCustomer() {
        service.getCustomer(ID);
        verify(repository, times(1)).findById(ID);
    }

    @Test
    void shouldCallDeleteByIdWhenDeleteCustomer() {
        service.deleteCustomer(ID);
        verify(repository, times(1)).deleteById(ID);
    }

    @Test
    void shouldCallUpdateWhenUpdateCustomer() {
        service.updateCustomer(ID, CUSTOMERMODEL);
        verify(repository, times(1)).update(ID, CUSTOMERMODEL);
    }
}
