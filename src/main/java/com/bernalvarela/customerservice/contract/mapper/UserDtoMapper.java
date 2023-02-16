package com.bernalvarela.customerservice.contract.mapper;

import com.bernalvarela.customerservice.application.vo.UserVO;
import com.bernalvarela.customerservice.openapi.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    User domainToDto(com.bernalvarela.customerservice.domain.model.User user);

    List<User> domainToDto(List<com.bernalvarela.customerservice.domain.model.User> users);

    UserVO dtoToVO(User user);

}
