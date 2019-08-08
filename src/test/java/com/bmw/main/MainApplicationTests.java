package com.bmw.main;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.bmw.entity.ResponseRepairOrder;
import com.bmw.main.service.CDKService;
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
	
	@Autowired
	private CDKService cDKClient;
	
	@Test
	public void contextLoads() throws IOException {
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//		mapper.setDateFormat(df);
		List<RepairOrder> repairOrderList = new ArrayList<RepairOrder>();
		Collection<RepairOrderDetail> repairOrderDetailList = Collections.synchronizedCollection(new ArrayList<RepairOrderDetail>());
		String str = cDKClient.fetchRepairOrders("MBOBI", "987501","2019-08-07");
//		JsonNode node = mapper.readTree(str);
//		ResponseRepairOrder responseRepairOrder= mapper.treeToValue(node, ResponseRepairOrder.class);
		ResponseRepairOrder responseRepairOrder = mapper.readValue(str, ResponseRepairOrder.class);
//		responseRepairOrder.getItems().get(0).setRepairOrderId("123213");
		if(responseRepairOrder.getItems() !=null){
			List<RepairOrderDetail> l = responseRepairOrder.getItems().parallelStream().map(repairOrder -> {
				String detailStr = cDKClient.fetchRepairOrderById("MBOBI", "987501",repairOrder.getRepairOrderId());
				try {
					return mapper.readValue(detailStr, RepairOrderDetail.class);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			})
			.filter(predicate -> StringUtils.isNotBlank(predicate.getRepairOrderId()))
			.collect(Collectors.toList());
			l.stream().forEachOrdered(System.out::println);
		}
	}
}
