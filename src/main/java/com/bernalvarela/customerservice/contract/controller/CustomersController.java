package com.bernalvarela.customerservice.contract.controller;

import com.bernalvarela.customerservice.application.service.CustomerService;
import com.bernalvarela.customerservice.application.service.ImageService;
import com.bernalvarela.customerservice.application.vo.ImageVO;
import com.bernalvarela.customerservice.contract.mapper.CustomerDtoMapper;
import com.bernalvarela.customerservice.contract.mapper.ImageDtoMapper;
import com.bernalvarela.customerservice.domain.exception.ElementNotFoundException;
import com.bernalvarela.customerservice.openapi.api.CustomersApi;
import com.bernalvarela.customerservice.openapi.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
public class CustomersController implements CustomersApi {

    @Autowired
    private CustomerService service;

    @Autowired ImageService imageService;

    @Autowired
    private CustomerDtoMapper mapper;

    @Autowired
    private ImageDtoMapper imageMapper;

    @Override
    public ResponseEntity<Customer> addCustomer(
            @Parameter(name = "customer", description = "") @Valid @RequestParam(value = "customer", required = false) Object customer,
            @Parameter(name = "profileImage", description = "") @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        Customer c;
        try {
            c = new ObjectMapper().readValue(customer.toString(), Customer.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }

        ImageVO image = null;
        try {
            image = imageMapper.dtoToVO(profileImage);
        } catch (IOException e) {
            log.error("ERROR READING IMAGE");
        }
        Customer createdCustomer = mapper.domainToDto(service.createCustomer(mapper.dtoToVO(c), image));
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
            return ResponseEntity.noContent().build();
        } catch (ElementNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> updateCustomer(
            @Parameter(name = "id", description = "", required = true) @PathVariable("id") Long id,
            @Parameter(name = "customer", description = "") @Valid @RequestParam(value = "customer", required = false) Object customer,
            @Parameter(name = "profileImage", description = "") @RequestPart(value = "profileImage", required = false) MultipartFile profileImage
    ) {
        Customer c = null;
        if (Objects.nonNull(customer)) {
            try {
                c = new ObjectMapper().readValue(customer.toString(), Customer.class);
            } catch (JsonProcessingException e) {
                return ResponseEntity.badRequest().build();
            }
        }

        ImageVO image = null;
        if (Objects.nonNull(profileImage)) {
            try {
                image = imageMapper.dtoToVO(profileImage);
            } catch (IOException e) {
                log.error("ERROR READING IMAGE");
            }
        }

        try {
            service.updateCustomer(id, mapper.dtoToVO(c), image);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteCustomerImage(
            @Parameter(name = "id", description = "", required = true) @PathVariable("id") Long id
    ) {
        try {
            service.deleteCustomerImage(id);
            return ResponseEntity.noContent().build();
        } catch (ElementNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}