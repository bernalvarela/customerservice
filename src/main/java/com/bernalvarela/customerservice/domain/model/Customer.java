package com.bernalvarela.customerservice.domain.model;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
@Builder
public class Customer {

    long id;

    String name;

    String surname;

    String photo;

}