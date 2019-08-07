package com.bmw.main.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bmw.entity.Customer;
import com.bmw.entity.Result;
import com.bmw.feign.FeignClientConfig;
import com.bmw.main.service.impl.BackUPCall;

@FeignClient(name = "bmw-progress", fallback = BackUPCall.class, configuration = FeignClientConfig.class )
public interface FeignService {
	
//	@RequestLine("GET /progress/customer")
	@RequestMapping(method = RequestMethod.GET, value = "/progress/customer")
	Result fetchCustomer();
}