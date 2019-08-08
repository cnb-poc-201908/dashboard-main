package com.bmw.entity;

import java.util.List;

import lombok.Data;

@Data
public class ResponseRepairOrder {
	private int totalItems;
	private int totalPages;
	private List<RepairOrder> items;
}
