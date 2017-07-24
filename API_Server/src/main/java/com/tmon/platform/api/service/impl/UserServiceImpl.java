package com.tmon.platform.api.service.impl;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.UserDao;
import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.exception.PreconditionException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.service.UserService;
import com.tmon.platform.api.util.SessionManager;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	SessionManager sessionManager;
	
	public UserDto user(String session){
		String user_id = sessionManager.getUserId(session);
		return userDao.user(user_id);
	}
	
	/**
	 * @author gwlee
	 * @since 2017-07-13
	 */
	@SuppressWarnings("unchecked")
	public JSONObject login(String user_id, String user_pw) throws PreconditionException{
		UserDto userDto = userDao.login(user_id, user_pw);			
		//계정정보 없음
		if(userDto == null) 
			throw new PreconditionException(602, "Invalid User Information");
		
		JSONObject result = new JSONObject();
		String sessionValue = sessionManager.createSession(userDto);
		result.put("session", sessionValue);
		return result;
	}
	
	public JSONObject logout(String session) {
		JSONObject obj = new JSONObject();
		sessionManager.deleteSession(session);
		obj.put("msg", "Success Delete Session");
		return obj;
	}
	
	
	public JSONObject join(UserDto userDto) throws SQLCustomException {
		try {
			userDao.join(userDto); 
		}catch(Exception e) {
			throw new SQLCustomException(603, "Fail : User Join SQL Error");
		}
		JSONObject obj = new JSONObject();
		obj.put("msg","Join Success");
		return obj;
	}
	
	
}
