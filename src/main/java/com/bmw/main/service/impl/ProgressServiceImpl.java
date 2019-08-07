package com.bmw.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmw.main.exception.MainErrorException;
import com.bmw.main.service.CDKService;
import com.bmw.main.service.FeignService;
import com.bmw.main.service.MainService;

@Service
public class ProgressServiceImpl implements MainService {
	
//	@Autowired
//	private FeignService feignService;
	
//	@Autowired
//	private CDKClient cDKClient;
	
	@Override
	public String test(String id) {
		throw new MainErrorException();
	}

	@Override
	public String testPing() {
//		return cDKClient.ping("MBOBI", "987501");
//		return this.restTemplate.getForObject("https://stage.apigw.cdkapps.cn/service/MBOBI/987501/ping", String.class);
		return "";
	}

	
}
