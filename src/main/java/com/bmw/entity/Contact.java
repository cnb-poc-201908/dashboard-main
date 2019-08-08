package com.bmw.entity;

import lombok.Data;

@Data
public class Contact {

    private String name;
    private String phone;
    private String method;
    private Address address;

}