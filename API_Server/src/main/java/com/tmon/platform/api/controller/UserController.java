package com.tmon.platform.api.controller;

import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.service.UserService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@ApiOperation(value = "로그인 폼")
	@RequestMapping(value = "/loginForm", method = RequestMethod.GET)
	public String loginForm() {
		return "loginForm";
	}

	@ApiOperation(value = "회원가입 폼")
	@RequestMapping(value = "/joinForm", method = RequestMethod.GET)
	public String joinForm() {
		return "joinForm";
	}

	@ApiOperation(value = "로그인")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject login(@RequestParam("user_id") String user_id, @RequestParam("user_pw") String user_pw) {
		return userService.login(user_id, user_pw);
	}

	@ApiOperation(value = "회원가입")
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject join(@RequestParam("user_id") String user_id, @RequestParam("user_pw") String user_pw) {
		UserDto userDto = new UserDto();
		userDto.setUser_id(user_id);
		userDto.setUser_pw(user_pw);
		return userService.join(userDto);		
	}

	@ExceptionHandler(MySQLIntegrityConstraintViolationException.class)
	public JSONObject sqlExceptionHandler(SQLException e) {
		logger.error("Error Message : " + e);		
		JSONObject obj = new JSONObject();
		obj.put("msg", "JOIN error");	
		return obj;
	}
	
	@ExceptionHandler(NullPointerException.class)
	public JSONObject nullException(NullPointerException e) {
		logger.error("Error Message : " + e);
		JSONObject obj = new JSONObject();
		obj.put("msg", "LOGIN error");	
		return obj;
	}

}
