package com.bernalvarela.customerservice.application.service;

import com.bernalvarela.customerservice.application.mapper.CustomerVOMapper;
import com.bernalvarela.customerservice.application.vo.CustomerVO;
import com.bernalvarela.customerservice.application.vo.ImageVO;
import com.bernalvarela.customerservice.domain.model.Customer;
import com.bernalvarela.customerservice.domain.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
@Slf4j
public class CustomerService {

    private ImageService imageService;

    private CustomerRepository repository;

    private CustomerVOMapper mapper;

    public Customer createCustomer(CustomerVO c, ImageVO image) {
        if (Objects.nonNull(image)) {
            try {
                String path = imageService.uploadImage(image);
                c.setPhoto(path);
            } catch (IOException e) {
                log.error("ERROR UPLOADING IMAGE");
            }
        }
        return repository.save(mapper.voToDomain(c));
    }

    public List<Customer> getCustomers() {
        return repository.findAll();
    }

    public Customer getCustomer(Long id) {
        return repository.findById(id);
    }

    public void deleteCustomer(Long id) {
        repository.deleteById(id);
    }

    public void updateCustomer(Long id, CustomerVO customer, ImageVO image) {
        boolean updated = false;
        CustomerVO c = mapper.domainToVO(repository.findById(id));

        if (Objects.nonNull(image) && Objects.nonNull(image.getName()) && Objects.nonNull(image.getContent())) {
            try {
                String path = imageService.uploadImage(image);
                imageService.deleteImage(c.getPhoto());
                c.setPhoto(path);
                updated = true;
            } catch (IOException ignore) {
            }
        }

        if (Objects.nonNull(customer)) {
            if (Objects.nonNull(customer.getName()) && !customer.getName().isEmpty()) {
                c.setName(customer.getName());
                updated = true;
            }
            if (Objects.nonNull(customer.getSurname()) && !customer.getSurname().isEmpty()) {
                c.setSurname(customer.getSurname());
                updated = true;
            }
        }

        if (updated) {
            repository.save(mapper.voToDomain(c));
        }
    }

    public void deleteCustomerImage(Long id) {
        try {
            CustomerVO c = mapper.domainToVO(repository.findById(id));
            imageService.deleteImage(c.getPhoto());
            c.setPhoto(null);
            repository.save(mapper.voToDomain(c));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
