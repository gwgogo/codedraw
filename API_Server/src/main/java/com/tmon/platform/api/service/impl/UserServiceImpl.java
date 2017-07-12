package com.tmon.platform.api.service.impl;

import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.tmon.platform.api.controller.UserController;
import com.tmon.platform.api.dao.UserDao;
import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	//private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserDao userDao;
	
	public JSONObject login(String user_id, String user_pw) {
		
		UserDto userDto = userDao.login(user_id);
	
		if(!user_pw.equals(userDto.getUser_pw())) {
			throw new NullPointerException();
		}
		
		JSONObject obj = new JSONObject();
		obj.put("msg", "LOGIN success");
		
		return obj;
	}
	
	public JSONObject join(UserDto userDto) {
		userDao.join(userDto);
		
		JSONObject obj = new JSONObject();
		obj.put("msg", "JOIN success");
		
		return obj;
	}
	
}
