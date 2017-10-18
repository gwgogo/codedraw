package kr.co.rentwheel.dao;


import java.util.List;

import kr.co.rentwheel.dto.UserDto;


public interface UserDao {
	public UserDto userLogin(String u_id);
	public UserDto userJoinDuplicationValidate(String u_id);
	public void userJoin(UserDto user);
	public List<UserDto> getUserAll();
	public UserDto getUserByUserID(String u_id);
	public void decreaseUserBalance(String u_id, int u_balance);
	public int increaseUserBalance(String u_id, int u_balance);
	public int increaseAdminBalance(int a_balance);
	public int decreaseAdminBalance(int a_balance);
	public int getAdminBalance();
	public int getUserBalance(String u_id);
}
