package com.bmw.main;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bmw.entity.RepairOrder;
import com.bmw.entity.RepairOrderDetail;
import com.bmw.entity.RepairOrderList;
import com.bmw.main.service.CDKService;
import com.bmw.util.JsonUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MainApplicationTests {

//	@Autowired
//    private ProgressService progressService;
//	
//	@Test
//	public void contextLoads() {
//		progressService.testPing();
//	}
	@Autowired
	ObjectMapper mapper = new ObjectMapper();
	
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	@Autowired
	private CDKService cDKClient;
	
	@Test
	public void contextLoads() throws IOException {
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		LocalDateTime now = LocalDateTime.now();
        String nowText = now.format(formatter);
//		mapper.setDateFormat(df);
//		List<RepairOrder> repairOrderList = new ArrayList<RepairOrder>();
		Collection<RepairOrderDetail> repairOrderDetailList = Collections.synchronizedCollection(new ArrayList<RepairOrderDetail>());
		String str = cDKClient.fetchRepairOrders("MBOBI", "987501",nowText);
//		JsonNode node = mapper.readTree(str);
//		ResponseRepairOrder responseRepairOrder= mapper.treeToValue(node, ResponseRepairOrder.class);
		RepairOrderList repairOrderList = mapper.readValue(str, RepairOrderList.class);
//		responseRepairOrder.getItems().get(0).setRepairOrderId("123213");
		if(repairOrderList.getItems() !=null){
			List<RepairOrderDetail> l = repairOrderList.getItems().parallelStream().map(repairOrder -> {
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
					return r;
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			})
			.filter(predicate -> StringUtils.isNotBlank(predicate.getRepairOrderId()))
			.collect(Collectors.toList());
			l.stream().forEachOrdered(System.out::println);
			System.out.println(repairOrderList);
		}
	}
}
