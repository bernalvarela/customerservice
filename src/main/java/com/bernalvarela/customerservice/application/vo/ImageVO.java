package com.bernalvarela.customerservice.application.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
@Getter
@Setter
public class ImageVO {

    String name;

    byte[] content;

}