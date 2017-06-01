package kr.co.rentwheel.mapper;

import java.util.List;

import kr.co.rentwheel.dto.UserDto;

public interface UserMapper {
	public UserDto login(String u_id);
	public boolean isExistId(String u_id);
	public boolean userJoin(UserDto user);
}
