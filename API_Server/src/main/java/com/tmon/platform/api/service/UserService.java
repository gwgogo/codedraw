package com.tmon.platform.api.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.util.CustomException;

public interface UserService {

	public JSONObject join(UserDto userDto) throws Exception;
	public UserDto user(String session);
	public JSONObject login(String user_id, String user_pw) throws CustomException;
	public JSONObject logout(String session) throws CustomException;
}
