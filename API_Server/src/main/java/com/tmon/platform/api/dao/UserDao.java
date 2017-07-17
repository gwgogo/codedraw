package com.tmon.platform.api.dao;

import com.tmon.platform.api.dto.UserDto;

public interface UserDao {
	public UserDto login(String user_id, String user_pw);
	public UserDto user(String user_id);
	public void join(UserDto userDto);
}
