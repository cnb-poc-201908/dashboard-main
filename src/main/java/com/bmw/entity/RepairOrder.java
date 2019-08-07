package com.bmw.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class RepairOrder {

	private String repairOrderId;
    private String status;
    private String notes;
    private String checkInDateTime;
    private String checkOutDateTime;
    private int checkInMileage;
    private int checkOutMileage;
    private String onHoldReason;
    private String vehicleId;
    private String vehicleClass;
    private String vehicleDescription;
    private String vehicleVin;
    private String vehiclelicensePlate;
    private String vehicleEngineNumber;
    private int vehicleMileage;
    private String customerId;
    private String companyId;
    private String contactName;
    private String contactPhone;
    private String assignedAdvisorId;
    private String assignedAdvisorName;
    private String contactAdvisorId;
    private String contactAdvisorName;
    private String appointmentNotes;
    private String bookingType;
    private Date dueInDateTime;
    private Date dueOutDateTime;
    private Date plannedIn;
    private Date plannedOut;
    private List<RepairOrderJob> jobs;
}
