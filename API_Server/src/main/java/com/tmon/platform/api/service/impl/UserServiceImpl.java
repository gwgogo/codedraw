package com.tmon.platform.api.service.impl;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.UserDao;
import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.service.UserService;
import com.tmon.platform.api.util.CustomException;

@Service
public class UserServiceImpl implements UserService {
	//private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserDao userDao;
	
	public boolean login(String user_id, String user_pw) {
		UserDto userDto = userDao.login(user_id);
		if(userDto == null || !user_pw.equals(userDto.getUser_pw())) {
			return false;
		}	
		return true;
	}
	/*public JSONObject login(String user_id, String user_pw) throws CustomException{
		
		try {
			UserDto userDto = userDao.login(user_id);
			if(!user_pw.equals(userDto.getUser_pw())) {
				throw new CustomException("Login Error(not correspond PW");
			}
		}catch(NullPointerException e) {
			throw new CustomException("Login Error(not exist ID)");
		}		
		
		JSONObject obj = new JSONObject();
		obj.put("msg", "LOGIN success");
		
		return obj;
	}*/
	
	public JSONObject join(UserDto userDto) throws CustomException {
		
		try {
			userDao.join(userDto); 
		}catch(Exception e) {
			throw new CustomException("JOIN Error");
		}
		
		JSONObject obj = new JSONObject();
		obj.put("msg", "JOIN success");
		
		return obj;
	}
	
}
