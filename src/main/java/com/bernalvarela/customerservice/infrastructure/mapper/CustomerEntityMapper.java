package com.bernalvarela.customerservice.infrastructure.mapper;

import com.bernalvarela.customerservice.infrastructure.entity.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerEntityMapper {

        Customer domainToEntity(com.bernalvarela.customerservice.domain.model.Customer customer);

        com.bernalvarela.customerservice.domain.model.Customer entityToDomain(Customer customer);

        List<com.bernalvarela.customerservice.domain.model.Customer> entityToDomain(List<Customer> customers);

}
