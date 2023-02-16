package com.bernalvarela.customerservice.application.mapper;

import com.bernalvarela.customerservice.application.vo.CustomerVO;
import com.bernalvarela.customerservice.domain.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerVOMapper {

    Customer voToDomain(CustomerVO customer);

    CustomerVO domainToVO(Customer customer);
}
