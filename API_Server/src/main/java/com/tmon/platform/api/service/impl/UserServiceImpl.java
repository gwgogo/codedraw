package com.tmon.platform.api.service.impl;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.UserDao;
import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.exception.CustomException;
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
	public JSONObject login(String user_id, String user_pw) throws CustomException{
		UserDto userDto = userDao.login(user_id, user_pw);			
			
		//계정정보 없음
		if(userDto == null) 
			throw new CustomException(501, "Invalid User Information");
		
		JSONObject result = new JSONObject();
		String sessionValue = sessionManager.createSession(userDto);
		
		result.put("session", sessionValue);
		
		//logger.info("user login.\n" + "user_id : " + user_id + ", sessionValue : " + sessionvalue);
		
		return result;
	}
	
	public JSONObject logout(String session) throws CustomException{
		
		JSONObject obj = new JSONObject();
		if(session == null) {
			throw new CustomException(501, "Invalid Session");
		}
		sessionManager.deleteSession(session);
		obj.put("msg", "Success Delete Session");
		return obj;
	}
	
	
	public JSONObject join(UserDto userDto) throws CustomException {
		try {
			userDao.join(userDto); 
		}catch(Exception e) {
			throw new CustomException( 501, "Join Error(duplication ID)");
		}
		JSONObject obj = new JSONObject();
		obj.put("msg","Join Success");
		return obj;
	}
	
	
}
