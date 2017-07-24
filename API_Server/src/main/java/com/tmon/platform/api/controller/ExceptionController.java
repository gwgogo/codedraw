package com.tmon.platform.api.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tmon.platform.api.exception.ProductException;
import com.tmon.platform.api.exception.UserException;

@ControllerAdvice
@RestController
public class ExceptionController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public String ExceptionHandler(Exception e)  {
		e.printStackTrace();
		logger.error("Error Message : " + e.getMessage());			
		//throw new CustomException(501, e.getMessage());	// 에러로 리턴			
		return e.getMessage();	// 코드 200(성공)으로 리턴
	}
	
	
	@ResponseStatus(value=HttpStatus.PRECONDITION_FAILED)	// 412(사전조건 실패)
	@ExceptionHandler(UserException.class)
	public Map UserExceptionHandler(UserException e)  {
		e.printStackTrace();
		return e.getErrorStatus();			
	}
		
	
	@ResponseStatus(value=HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)	// 416(처리할 수 없는 요청범위)
	@ExceptionHandler(ProductException.class)
	public Map ProductExceptionHandler(ProductException e) {
		e.printStackTrace();
		return e.getErrorStatus();
	}
	
	
}
