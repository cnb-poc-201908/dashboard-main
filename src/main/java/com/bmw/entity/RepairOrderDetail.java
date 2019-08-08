/**
  * Copyright 2019 bejson.com 
  */
package com.bmw.entity;
import java.util.List;

import lombok.Data;

@Data
public class RepairOrderDetail {

    private String repairOrderId;
    private String status;
    private Details details;
    private Vehicle vehicle;
    private Contact contact;
    private Resources resources;
    private Appointment appointment;
    private Planning planning;
    private List<Jobs> jobs;
   

}