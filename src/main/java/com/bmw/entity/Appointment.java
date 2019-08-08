package com.bmw.entity;
import java.util.Date;

import lombok.Data;

@Data
public class Appointment {

    private String notes;
    private BookingOptions bookingOptions;
    private Date dueInDateTime;
    private Date dueOutDateTime;
    public void setNotes(String notes) {
         this.notes = notes;
     }
     public String getNotes() {
         return notes;
     }

}