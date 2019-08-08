package com.bmw.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.bmw.entity.RepairOrder;
import com.bmw.entity.RepairOrderDetail;
import com.bmw.entity.ResponseRepairOrder;
import com.bmw.main.service.CDKService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j

@Component
public class DataStore implements ApplicationRunner{

	private static final List<RepairOrder> repairOrderList = new ArrayList<RepairOrder>();
	private static final List<RepairOrderDetail> repairOrderDetailList = new ArrayList<RepairOrderDetail>();
	
	final static ObjectMapper mapper = new ObjectMapper();
	
	final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
	@Autowired
	private CDKService cDKClient;
	
	public static List<RepairOrder> getOrders(){
		return repairOrderList;
	}
	
	public static List<RepairOrderDetail> getOrdersDetails(){
		return repairOrderDetailList;
	}
	
	public List<RepairOrder> test(){
		return repairOrderList;
	}
	@Override
	public void run(ApplicationArguments args) throws Exception {
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		LocalDateTime now = LocalDateTime.now();
        String nowText = now.format(formatter);
		String str = cDKClient.fetchRepairOrders("MBOBI", "987501",nowText);
//		JsonNode node = mapper.readTree(str);
//		int totalItems = node.path("totalItems").asInt();
//		if(totalItems>0){
//			repairOrderList.addAll(mapper.treeToValue(node.get("items"), List.class));
//		}
		ResponseRepairOrder responseRepairOrder = mapper.readValue(str, ResponseRepairOrder.class);
		if(responseRepairOrder.getItems() !=null){
			repairOrderList.addAll(responseRepairOrder.getItems());
			repairOrderDetailList.addAll(responseRepairOrder.getItems().parallelStream().map(repairOrder -> {
				String detailStr = cDKClient.fetchRepairOrderById("MBOBI", "987501",repairOrder.getRepairOrderId());
				try {
					return mapper.readValue(detailStr, RepairOrderDetail.class);
				} catch (IOException e) {
					log.error(e.getMessage());
				}
				return null;
			})
			.filter(predicate -> StringUtils.isNotBlank(predicate.getRepairOrderId()))
			.collect(Collectors.toList()));
		}
	}
}
