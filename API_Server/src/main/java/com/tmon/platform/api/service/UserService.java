package com.tmon.platform.api.service;

import org.json.simple.JSONObject;

import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.exception.CustomException;
import com.tmon.platform.api.exception.UserException;

public interface UserService {

	public JSONObject join(UserDto userDto) throws UserException;
	public UserDto user(String session);
	public JSONObject login(String user_id, String user_pw) throws UserException;
	public JSONObject logout(String session) throws UserException;
}
