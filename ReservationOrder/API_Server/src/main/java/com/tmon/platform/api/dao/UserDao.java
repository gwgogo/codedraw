package com.tmon.platform.api.dao;

import com.tmon.platform.api.dto.UserDto;

public interface UserDao {
	public UserDto login(String userID, String userPW);
	public UserDto user(String userID);
}
