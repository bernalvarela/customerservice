package com.bernalvarela.customerservice.contract.mapper;

import com.bernalvarela.customerservice.application.vo.ImageVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Mapper(componentModel = "spring")
public interface ImageDtoMapper {

    @Mapping(target = "name", source = "image.name")
    @Mapping(target = "content", source = "image.bytes")
    ImageVO dtoToVO(MultipartFile image) throws IOException;

}
