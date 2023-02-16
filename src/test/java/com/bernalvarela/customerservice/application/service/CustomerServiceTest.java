package com.bernalvarela.customerservice.application.service;

import com.bernalvarela.customerservice.application.mapper.CustomerVOMapper;
import com.bernalvarela.customerservice.application.vo.CustomerVO;
import com.bernalvarela.customerservice.application.vo.ImageVO;
import com.bernalvarela.customerservice.domain.model.Customer;
import com.bernalvarela.customerservice.domain.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private static CustomerRepository repository;

    @Mock
    private static ImageService imageService;

    private static CustomerVOMapper mapper = Mappers.getMapper(CustomerVOMapper.class);
    ;

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

    private static final CustomerVO CUSTOMER_VO = CustomerVO.builder()
            .id(ID)
            .name(NAME)
            .surname(SURNAME)
            .photo(PHOTO)
            .build();

    private static final ImageVO IMAGE_VO = ImageVO.builder()
            .name(NAME)
            .content("CONTENT".getBytes())
            .build();

    @BeforeEach
    public void init() {
        service = new CustomerService(imageService, repository, mapper);
    }

    @Test
    void shouldCallSaveInRepositoryWhenCreateCustomer() {
        service.createCustomer(CUSTOMER_VO, IMAGE_VO);
        verify(repository, times(1)).save(any());
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
        when(repository.findById(any())).thenReturn(CUSTOMERMODEL);
        service.updateCustomer(ID, CUSTOMER_VO, IMAGE_VO);
        verify(repository, times(1)).save(any());
    }

    @Test
    void shouldDontCallUpdateWhenUpdateCustomerNull() {
        when(repository.findById(any())).thenReturn(CUSTOMERMODEL);
        service.updateCustomer(ID, null, null);
        verify(repository, never()).save(any());
    }

    @Test
    void shouldCreateCustomerImageWhenNameAndContentNotNull() {
        when(repository.findById(any())).thenReturn(CUSTOMERMODEL);
        service.updateCustomer(ID, null, IMAGE_VO);
        verify(repository, times(1)).save(any());
    }
}
