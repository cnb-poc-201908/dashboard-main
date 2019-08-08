package com.bmw.main.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bmw.entity.Customer;
import com.bmw.entity.RepairOrder;
import com.bmw.entity.RepairOrderDetail;
import com.bmw.entity.Result;
import com.bmw.main.service.CDKService;
import com.bmw.main.service.FeignService;
import com.bmw.main.service.MainService;
import com.bmw.util.DataStore;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@RestController
@RequestMapping("/repair-orders")
public class MainController {
	
	@Autowired
    private MainService progressService;
	
	@Autowired
	private FeignService feignService;
	
	@Autowired
	private CDKService cDKClient;
	
	@GetMapping("/test")
	public String test() throws JsonProcessingException {
		log.info("test.");
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = new HashMap<>();
        map.put("success", "OK");
		return objectMapper.writeValueAsString(map);
	}
	
	@GetMapping("/test/{id}")
    public Result testId(@PathVariable("id") String id) {
        return new Result().success(progressService.test(id));
    }
	
	@GetMapping("/ping")
    public Result testPing() {
        return new Result().success(cDKClient.ping("MBOBI", "987501"));
    }
	
	@GetMapping("/customer")
    public Result fetchCustomer() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		InputStream is = MainController.class.getResourceAsStream("/customer.json");
        Customer customer = objectMapper.readValue(is, Customer.class);
        return new Result().success(customer);
    }
	
	@GetMapping("/customer2")
    public Result fetchCustomer2() throws JsonParseException, JsonMappingException, IOException {
		Result result = feignService.fetchCustomer();
        return new Result().success(result.getData());
    }
	
	@GetMapping("/orders")
    public Result repairOrders() throws JsonParseException, JsonMappingException, IOException {
        return new Result().success(DataStore.getOrders());
    }
	
	@GetMapping("/order/{id}")
    public Result fetchRepairDetailById(@ApiParam("repairOrderId") @PathVariable("id") String id) {
        return new Result().success(DataStore.getOrdersDetails().stream().filter(order -> id.equals(order.getRepairOrderId())));
    }
	
	@PostMapping("/updateRepairOrder")
    public Result updateRepairOrder(@ApiParam("repairOrder") @RequestBody RepairOrder repairOrder) {
		
		Optional<RepairOrder> option = DataStore.getOrders().stream().filter(order-> repairOrder.getRepairOrderId().equals(order.getRepairOrderId())).findAny();
		if(option.isPresent()){
			Optional<RepairOrderDetail> op = DataStore.getOrdersDetails().stream().filter(detail-> repairOrder.getRepairOrderId().equals(detail.getRepairOrderId())).findAny();
			if(StringUtils.isNotBlank(repairOrder.getStatus()))
				option.get().setStatus(repairOrder.getStatus());
			if(op.isPresent())
				op.get().setStatus(repairOrder.getStatus());
			return new Result().success();
		}
		return new Result().fail("Not Found",400);
		
    }
	
	@GetMapping("/queryRepairOrder/{queryStr}")
    public Result queryRepairOrder(@ApiParam("queryStr") @PathVariable("queryStr") String queryStr) {
       return new Result().success(DataStore.getOrders().parallelStream().filter(order ->
		{
			Optional<RepairOrderDetail> aaa = DataStore.getOrdersDetails().parallelStream()
					.filter(detail -> (detail.getVehicle().getIdentification().getVin().contains(queryStr) 
									|| detail.getVehicle().getIdentification().getLicensePlate().contains(queryStr)) 
									&& order.getRepairOrderId().equals(detail.getRepairOrderId())
						).findAny();
			return aaa.isPresent();
		}).collect(Collectors.toList()));
    }
	
}
