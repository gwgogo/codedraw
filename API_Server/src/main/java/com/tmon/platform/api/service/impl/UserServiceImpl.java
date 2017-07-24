package com.tmon.platform.api.service.impl;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.UserDao;
import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.exception.CustomException;
import com.tmon.platform.api.exception.UserException;
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
	public JSONObject login(String user_id, String user_pw) throws UserException{
		UserDto userDto = userDao.login(user_id, user_pw);			
			
		//계정정보 없음
		if(userDto == null) 
			throw new UserException(601, "Invalid User Information");
		
		JSONObject result = new JSONObject();
		String sessionValue = sessionManager.createSession(userDto);
		
		result.put("session", sessionValue);
		
		return result;
	}
	
	public JSONObject logout(String session) throws UserException{
		
		JSONObject obj = new JSONObject();
		if(session == null) {
			throw new UserException(602, "Invalid Session");
		}
		sessionManager.deleteSession(session);
		obj.put("msg", "Success Delete Session");
		return obj;
	}
	
	
	public JSONObject join(UserDto userDto) throws UserException {
		try {
			userDao.join(userDto); 
		}catch(Exception e) {
			throw new UserException( 603, "Join Error(duplication ID)");
		}
		JSONObject obj = new JSONObject();
		obj.put("msg","Join Success");
		return obj;
	}
	
	
}
