package com.bernalvarela.customerservice.domain.model;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class User {

    Long id;

    String name;

    String surname;

    String username;

    Boolean admin;

}