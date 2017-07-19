package com.tmon.platform.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ExceptionController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@ExceptionHandler(Exception.class)
	public String ExceptionHandler(Exception e) {
		e.printStackTrace();
		logger.error("Error Message : " + e.getMessage());			
		return e.getMessage();
	}
}
