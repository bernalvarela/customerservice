package com.bernalvarela.customerservice.application.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
@Getter
@Setter
public class CustomerVO {

    Long id;

    String name;

    String surname;

    String photo;

}