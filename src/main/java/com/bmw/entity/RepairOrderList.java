
package com.bmw.entity;
import java.util.List;

import lombok.Data;

@Data
public class RepairOrderList {

    private int totalItems;
    private int totalPages;
    private List<Items> items;
    private String links;

}