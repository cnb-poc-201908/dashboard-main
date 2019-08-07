package com.bmw.main.exception;

import com.bmw.exception.ServiceException;

public class MainErrorException extends ServiceException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainErrorException() {
        super(10400, "车辆没找到");
    }
}
