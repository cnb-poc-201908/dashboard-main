/**
  * Copyright 2019 bejson.com 
  */
package com.bmw.entity;
import java.util.Date;

import com.bmw.config.CustomJsonDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

@Data
public class Details {

    private String reference;
    private String notes;
    private Date checkInDateTime;
    private String checkOutDateTime;
    private CheckInMileage checkInMileage;
    private CheckOutMileage checkOutMileage;
    private String onHoldReason;

}