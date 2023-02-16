package com.bernalvarela.customerservice.application.mapper;

import com.bernalvarela.customerservice.application.vo.UserVO;
import com.bernalvarela.customerservice.domain.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserVOMapper {

    UserVO domainToVO(User user);

    List<UserVO> domainToVO(List<User> users);

    User voToDomain(UserVO user);

}
