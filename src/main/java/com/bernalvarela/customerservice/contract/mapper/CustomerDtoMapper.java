package com.bernalvarela.customerservice.contract.mapper;

import com.bernalvarela.customerservice.application.vo.CustomerVO;
import com.bernalvarela.customerservice.openapi.model.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerDtoMapper {

    Customer domainToDto(com.bernalvarela.customerservice.domain.model.Customer customer);

    List<Customer> domainToDto(List<com.bernalvarela.customerservice.domain.model.Customer> customers);

    CustomerVO dtoToVO(Customer customer);

}
