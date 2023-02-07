package com.bernalvarela.customerservice.contract.mapper;

import com.bernalvarela.customerservice.openapi.model.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

        Customer domainToDto(com.bernalvarela.customerservice.domain.entity.Customer customer);

        List<Customer> domainToDto(List<com.bernalvarela.customerservice.domain.entity.Customer> customers);

        com.bernalvarela.customerservice.domain.entity.Customer dtoToDomain(Customer customer);

}
