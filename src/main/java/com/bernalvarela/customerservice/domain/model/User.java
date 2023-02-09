package com.bernalvarela.customerservice.domain.model;

import lombok.Builder;
import lombok.Getter;
@Builder
@Getter
public class User {
    long id;
    String name;
    String surname;
    String username;
    Boolean admin;

}