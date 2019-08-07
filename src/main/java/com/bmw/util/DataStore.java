package com.bmw.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.bmw.entity.RepairOrder;
import com.bmw.main.controllers.MainController;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataStore {

	private static final List<RepairOrder> repairOrderList = new ArrayList<RepairOrder>();
	
	@PostConstruct
    public void readConfig() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
    	InputStream is = MainController.class.getResourceAsStream("/repair-orders.json");
    	repairOrderList.addAll(objectMapper.readValue(is, List.class));
    }
	
	public static List<RepairOrder> getOrders(){
		return repairOrderList;
	}
}
