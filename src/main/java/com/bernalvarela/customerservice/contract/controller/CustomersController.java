package com.bernalvarela.customerservice.contract.controller;

import com.bernalvarela.customerservice.application.service.CustomerService;
import com.bernalvarela.customerservice.contract.mapper.CustomerDtoMapper;
import com.bernalvarela.customerservice.domain.exception.ElementNotFoundException;
import com.bernalvarela.customerservice.openapi.api.CustomersApi;
import com.bernalvarela.customerservice.openapi.model.Customer;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CustomersController implements CustomersApi {

    @Autowired
    private CustomerService service;

    @Autowired
    private CustomerDtoMapper mapper;

    @Override
    public ResponseEntity<Customer> addCustomer(@Valid Customer customer) {
        Customer createdCustomer = mapper.domainToDto(service.createCustomer(mapper.dtoToDomain(customer)));
        return ResponseEntity.ok(createdCustomer);
    }

    @Override
    public ResponseEntity<Customer> getCustomer(
            @Parameter(name = "id", description = "", required = true) @PathVariable("id") Long id
    ) {
        try {
            return ResponseEntity.ok(mapper.domainToDto(service.getCustomer(id)));
        } catch (ElementNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(mapper.domainToDto(service.getCustomers()));
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(name = "id", description = "", required = true) @PathVariable("id") Long id
    ) {
        try {
            service.deleteCustomer(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ElementNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> updateCustomer(
            @Parameter(name = "id", description = "", required = true) @PathVariable("id") Long id,
            @Parameter(name = "Customer", description = "Update a customer", required = true) @Valid @RequestBody Customer customer
    ) {
        try {
            service.updateCustomer(id, mapper.dtoToDomain(customer));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}