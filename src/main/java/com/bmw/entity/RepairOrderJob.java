package com.bmw.entity;

import lombok.Data;

@Data
public class RepairOrderJob {
	private String jobId;
	private String jobType;
	private String jobSource;
	private String description;
}
