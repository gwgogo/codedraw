package kr.co.rentwheel.dao;

import java.util.List;

import kr.co.rentwheel.dto.UserDto;


public interface LoginDao {
	public UserDto login(String u_id);
	public boolean isExistId(String u_id);
	public boolean userJoin(UserDto user);
}
