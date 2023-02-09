package com.bernalvarela.customerservice.domain.model;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class Customer {

    Long id;

    String name;

    String surname;

    String photo;

}