package com.tmon.platform.api.service;

import org.json.simple.JSONObject;

import com.tmon.platform.api.dto.UserDto;

public interface UserService {
	public JSONObject login(String user_id, String user_pw);
	public JSONObject join(UserDto userDto);
}
