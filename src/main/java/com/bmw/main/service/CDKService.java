package com.bmw.main.service;

import feign.Param;
import feign.RequestLine;

public interface CDKService {

	@RequestLine("GET /{contractcode}/{businessunit}/ping")
    String ping(@Param("contractcode") String contractcode,@Param("businessunit") String businessunit);
	
	@RequestLine("GET /{contractcode}/{businessunit}/v1/repair-orders?DueInDateFrom={DueInDateFrom}")
	String fetchRepairOrders(@Param("contractcode") String contractcode,@Param("businessunit") String businessunit, @Param("DueInDateFrom") String DueInDateFrom);
	
	@RequestLine("GET /{contractcode}/{businessunit}/v2/repair-orders/{repairOrderId}")
	String fetchRepairOrderById(@Param("contractcode") String contractcode,@Param("businessunit") String businessunit, @Param("repairOrderId") String repairOrderId );
}
