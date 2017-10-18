package kr.co.rentwheel.mapper;

import java.util.List;
import java.util.Map;

import kr.co.rentwheel.dto.UserDto;

public interface UserMapper {
	public UserDto userLogin(String u_id);
	public boolean userJoin(UserDto user);
	public List<UserDto> getUserAll();
	public UserDto getUserByUserID(String u_id);
	public void decreaseUserBalance(Map<String,String> map);
	public int increaseUserBalance(Map<String,String> map);
	public int increaseAdminBalance(int a_balance);
	public int decreaseAdminBalance(int a_balance);
	public int getAdminBalance();
	public int getUserBalance(String u_id);
}
