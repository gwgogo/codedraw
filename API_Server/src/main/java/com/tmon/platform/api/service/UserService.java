package com.tmon.platform.api.service;

import java.sql.SQLException;

import org.json.simple.JSONObject;

import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.util.CustomException;

public interface UserService {
	//public JSONObject login(String user_id, String user_pw) throws CustomException;
	public boolean login(String user_id, String user_pw);
	public JSONObject join(UserDto userDto) throws CustomException;
}
