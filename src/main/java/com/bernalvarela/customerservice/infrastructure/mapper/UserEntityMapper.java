package com.bernalvarela.customerservice.infrastructure.mapper;

import com.bernalvarela.customerservice.infrastructure.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    User domainToEntity(com.bernalvarela.customerservice.domain.model.User user);

    com.bernalvarela.customerservice.domain.model.User entityToDomain(User user);

    List<com.bernalvarela.customerservice.domain.model.User> entityToDomain(List<User> users);

}
