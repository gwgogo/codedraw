package com.tmon.platform.api.service;

import org.json.simple.JSONObject;

import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.PreConditionException;

public interface UserService {

	public UserDto user(String session);
	public void userValid(String userID) throws NullCustomException;
	public JSONObject login(String userID, String userPW) throws PreConditionException;
	public JSONObject logout(String session);

}
