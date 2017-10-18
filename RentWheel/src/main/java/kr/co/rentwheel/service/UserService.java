package kr.co.rentwheel.service;

import java.util.List;

import kr.co.rentwheel.domain.CustomException;
import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.UserDto;


public interface UserService {
	public ResponseItem userLogin(String u_id, String u_pw);
	public ResponseItem userJoinDuplicationValidate(String u_id);
	public ResponseItem userJoin(UserDto user);
	public List<UserDto> getUserAll();
	public UserDto getUserByUserID(String u_id);
	public void decreaseUserBalance(String u_id, int u_balance) throws CustomException;
	public ResponseItem increaseUserBalance(String u_id, int u_balance) throws CustomException;
	public void increaseAdminBalance(int a_balance) throws CustomException;
	public void decreaseAdminBalance(int a_balance) throws CustomException;
	public int getUserBalance(String u_id);
}
