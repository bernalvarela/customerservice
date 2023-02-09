package com.bernalvarela.customerservice.domain.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Customer {
    long id;

    String name;

    String surname;

    String photo;

}