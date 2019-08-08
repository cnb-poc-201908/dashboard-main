package com.bmw.entity;

import lombok.Data;

@Data
public class Vehicle {

    private String vehicleId;
    private String class1;
    private String description;
    private Identification identification;
    private ReferenceData referenceData;
    private Mileage mileage;

}