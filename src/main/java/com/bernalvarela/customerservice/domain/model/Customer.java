package com.bernalvarela.customerservice.domain.model;

import lombok.*;

@Value
@ToString
@Builder
public class Customer {

    long id;

    String name;

    String surname;

    String photo;

}