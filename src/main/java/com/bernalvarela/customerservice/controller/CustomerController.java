package com.bernalvarela.customerservice.controller;

import com.bernalvarela.customerservice.api.CustomerApi;
import com.bernalvarela.customerservice.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomerController implements CustomerApi {

    @Override
    public ResponseEntity<String> addCustomer(@Valid Customer customer) {
        return ResponseEntity.ok("Customer with id " + customer.getId() + " is added");
    }

    @Override
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(List.of());
    }
}