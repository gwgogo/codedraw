package com.tmon.platform.api.service;

import org.json.simple.JSONObject;

import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.exception.PreConditionException;
import com.tmon.platform.api.exception.SQLCustomException;

public interface UserService {

	public JSONObject join(UserDto userDto) throws SQLCustomException;
	public UserDto user(String session);
	public JSONObject login(String userID, String userPW) throws PreConditionException;
	public JSONObject logout(String session);
}
