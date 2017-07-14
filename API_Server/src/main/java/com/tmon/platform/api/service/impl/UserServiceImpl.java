package com.tmon.platform.api.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.UserDao;
import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.service.UserService;
import com.tmon.platform.api.util.CustomException;
import com.tmon.platform.api.util.SessionManager;

@Service
public class UserServiceImpl implements UserService {
	//private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
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
	 * @param user_id 사용자명
	 * @param user_pw 사용자비밀번호
	 * @return 사용자정보 or null
	 * 
	 * 성공한 경우는 UserDto 를 그대로 json으로 보내줍니다.
	 * null인 경우(사용자 정보가 없는 경우)는 CustomException을 보냅니다.
	 */
	@SuppressWarnings("unchecked")
	public JSONObject login(String user_id, String user_pw) throws CustomException{
		UserDto userDto = userDao.login(user_id, user_pw);			
			
		//계정정보 없음
		if(userDto == null) 
			throw new CustomException("Invalid User Information");
		
		JSONObject result = new JSONObject();
		String sessionValue = sessionManager.createSession(userDto);
		
		result.put("session", sessionValue);
		
		//logger.info("user login.\n" + "user_id : " + user_id + ", sessionValue : " + sessionvalue);
		return result;
	}
	
	public JSONObject logout(String session) throws CustomException{
		
		JSONObject obj = new JSONObject();
		if(session == null) {
			throw new CustomException("Invalid Session");
		}
		sessionManager.deleteSession(session);
		obj.put("msg", "Success Delete Session");
		return obj;
	}
	
	
	public JSONObject join(UserDto userDto) throws Exception {
		try {
			userDao.join(userDto); 
		}catch(Exception e) {
			throw new SQLException("Join Error(duplication ID)");
		}
		JSONObject obj = new JSONObject();
		obj.put("msg","Join Success");
		return obj;
	}
	
	
}
