package com.bernalvarela.customerservice.contract.controller;

import com.bernalvarela.customerservice.application.service.CustomerService;
import com.bernalvarela.customerservice.contract.mapper.CustomerDtoMapper;
import com.bernalvarela.customerservice.openapi.api.CustomersApi;
import com.bernalvarela.customerservice.openapi.model.Customer;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomersController implements CustomersApi {

    @Autowired
    private CustomerService service;

    @Autowired
    private CustomerDtoMapper mapper;

    @Override
    public ResponseEntity<String> addCustomer(@Valid Customer customer) {
        Customer createdCustomer = mapper.domainToDto(service.createCustomer(mapper.dtoToDomain(customer)));
        return ResponseEntity.ok("Customer with id " + createdCustomer.getId() + " is added");
    }

    @Override
    public ResponseEntity<Customer> getCustomer(
            @Parameter(name = "id", description = "", required = true) @PathVariable("id") Long id
    ) {
        return service.getCustomer(id)
                .map(customer -> ResponseEntity.ok(mapper.domainToDto(customer)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<Customer>> getCustomers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(mapper.domainToDto(service.getCustomers()));
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(name = "id", description = "", required = true) @PathVariable("id") Long id
    ) {
        service.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateCustomer(
            @Parameter(name = "id", description = "", required = true) @PathVariable("id") Long id,
            @Parameter(name = "Customer", description = "Update a customer", required = true) @Valid @RequestBody Customer customer
    ) {
        service.updateCustomer(id, mapper.dtoToDomain(customer));
        return new ResponseEntity<>(HttpStatus.OK);

    }
}