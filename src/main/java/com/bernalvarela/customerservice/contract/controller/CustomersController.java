package com.bernalvarela.customerservice.contract.controller;

import com.bernalvarela.customerservice.application.service.CustomerService;
import com.bernalvarela.customerservice.contract.mapper.CustomerMapper;
import com.bernalvarela.customerservice.openapi.api.CustomersApi;
import com.bernalvarela.customerservice.openapi.model.Customer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomersController implements CustomersApi {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public ResponseEntity<String> addCustomer(@Valid Customer customer) {
        return ResponseEntity.ok("Customer with id " + customer.getId() + " is added");
    }

    @Override
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customerMapper.domainToDto(customerService.getCustomers()));
    }
}