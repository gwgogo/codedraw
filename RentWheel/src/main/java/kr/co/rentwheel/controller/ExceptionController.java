package kr.co.rentwheel.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import kr.co.rentwheel.domain.CustomException;

@ControllerAdvice
@RestController
public class ExceptionController {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);
	
	//@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED) // 412(사전조건 실패)
	@ExceptionHandler(CustomException.class)
	public Map<String, String> ExceptionHandler(CustomException e) {
		logger.error("Error Code : " + e.getErrorStatus().get("result") + ", Error Message : " + e.getErrorStatus().get("msg"));
		return e.getErrorStatus();
	}
}
