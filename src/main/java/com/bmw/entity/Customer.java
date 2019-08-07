package com.bmw.entity;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Customer {

	private Long id;
    private String name;
    private String email;
    private String phone;
    private int age;
    private String[] projects;
    private Address address;
    private List<String> paymentMethods;
    private Map<String, Object> profileInfo;
}
