package com.bmw.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.bmw.entity.Items;
import com.bmw.entity.RepairOrder;
import com.bmw.entity.RepairOrderDetail;
import com.bmw.entity.RepairOrderList;
import com.bmw.main.service.CDKService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j

@Component
public class DataStore{
//public class DataStore implements ApplicationRunner{

	private final List<Items> repairOrderList = new ArrayList<Items>();
	private final List<RepairOrderDetail> repairOrderDetailList = new ArrayList<RepairOrderDetail>();
	
	final ObjectMapper mapper = new ObjectMapper();
	
	final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
	@Autowired
	private CDKService cDKClient;
	
	public List<Items> getOrders(){
		return repairOrderList;
	}
	
	public List<RepairOrderDetail> getOrdersDetails(){
		return repairOrderDetailList;
	}
	
	public void init() throws JsonParseException, JsonMappingException, IOException{
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		LocalDateTime now = LocalDateTime.now();
        String nowText = now.format(formatter);
		String str = cDKClient.fetchRepairOrders("MBOBI", "987501",nowText);
		RepairOrderList list = mapper.readValue(str, RepairOrderList.class);
		if(list.getItems() !=null){
			repairOrderList.addAll(list.getItems());
			repairOrderDetailList.addAll(list.getItems().parallelStream().map(repairOrder -> {
				String detailStr = cDKClient.fetchRepairOrderById("MBOBI", "987501",repairOrder.getRepairOrderId());
				try {
					RepairOrderDetail r =  mapper.readValue(detailStr, RepairOrderDetail.class);
					repairOrder.setVehicleLicensePlate(r.getVehicle().getIdentification().getLicensePlate());
					repairOrder.setAmountLabor(JsonUtils.randomAmountLabor());
					repairOrder.setVehicleDescription(r.getVehicle().getDescription());
					repairOrder.setVehicleVIN(r.getVehicle().getIdentification().getVin());
					repairOrder.setAssignedAdvisorName(r.getResources().getAssignedAdvisor().getName());
					repairOrder.setDetailsNotes(r.getDetails().getNotes());
					repairOrder.setDueOutDateTime(r.getAppointment().getDueOutDateTime());
					repairOrder.setActivity(JsonUtils.randomActivity());
					log.info(repairOrder.toString());
					return r;
				} catch (IOException e) {
					log.error(e.getMessage());
				}
				return null;
			})
			.filter(predicate -> StringUtils.isNotBlank(predicate.getRepairOrderId()))
			.collect(Collectors.toList()));
		}
	}
	
//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//		init();
//	}
}
