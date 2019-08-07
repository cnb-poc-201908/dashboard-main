package com.bmw.main.service.impl;

import org.springframework.stereotype.Component;

import com.bmw.entity.Customer;
import com.bmw.entity.Result;
import com.bmw.main.service.FeignService;

@Component
public class BackUPCall implements FeignService{

	@Override
	public Result fetchCustomer() {
		return new Result().success(new Customer());
	}

}
