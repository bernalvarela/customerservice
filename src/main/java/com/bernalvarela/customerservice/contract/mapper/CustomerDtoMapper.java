package com.bernalvarela.customerservice.contract.mapper;

import com.bernalvarela.customerservice.openapi.model.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerDtoMapper {

        Customer domainToDto(com.bernalvarela.customerservice.domain.model.Customer customer);

        List<Customer> domainToDto(List<com.bernalvarela.customerservice.domain.model.Customer> customers);

        com.bernalvarela.customerservice.domain.model.Customer dtoToDomain(Customer customer);

}
