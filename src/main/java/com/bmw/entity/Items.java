package com.bmw.entity;

import java.util.Date;

import lombok.Data;

//@Builder
@Data
//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
public class Items {

	private String repairOrderId;
    private String internalRepairOrderId;
    private String vehicleId;
    private String customerId;
    private String companyId;
    private String advisorId;
    private String text;
    private String status;
    private Contact contact;
//    @Builder.Default
    private String vehicleLicensePlate;
    private String amountLabor;
    private boolean isActivity;
    private Date dueOutDateTime;
    private String vehicleDescription;
    private String vehicleVIN;
    private String assignedAdvisorName;
    private String detailsNotes;
}
