package kr.co.rentwheel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.rentwheel.dao.UserDao;
import kr.co.rentwheel.domain.CustomException;
import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.AdministratorDto;
import kr.co.rentwheel.dto.UserDto;
import kr.co.rentwheel.service.UserService;


@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;
	
	public ResponseItem userLogin(String u_id, String u_pw) {
		ResponseItem responseItem = new ResponseItem();
		UserDto user = userDao.userLogin(u_id);
		
		if(user == null){
			responseItem.setMsg("Fail : not exist ID");
			responseItem.setResult("401");
		}
		else if(!user.getU_pw().equals(u_pw)){
			responseItem.setMsg("Fail : not correct");
			responseItem.setResult("402");
		}
		return responseItem;	
	}
	
	public ResponseItem userJoinDuplicationValidate(String u_id){
		ResponseItem responseItem = new ResponseItem();
		if(userDao.userJoinDuplicationValidate(u_id) != null){
			responseItem.setMsg("Fail : already exist ID");
			responseItem.setResult("405");
		}
		return responseItem;
	}
	
	public ResponseItem userJoin(UserDto user) {
		ResponseItem responseItem = new ResponseItem();
		try{
			userDao.userJoin(user);
		}catch(Exception e) {
			responseItem.setMsg("Fail : User Join Error");
			responseItem.setResult("403");
		}
		return responseItem;
	}
	
	public int getUserBalance(String u_id) {
		return userDao.getUserBalance(u_id);
	}
	
	public List<UserDto> getUserAll(){
		return userDao.getUserAll();
	}
	
	public UserDto getUserByUserID(String u_id) {
		return userDao.getUserByUserID(u_id);
	}
	
	@Transactional(rollbackFor= CustomException.class) 
	public void decreaseUserBalance(String u_id, int u_balance) throws CustomException {
		UserDto userDto = userDao.getUserByUserID(u_id);
		int presentBalance = userDto.getU_balance();
		if(presentBalance < u_balance) {
			throw new CustomException("415", "Fail : User Low Balance");
		}
		userDao.decreaseUserBalance(u_id, u_balance);
	}
	
	@Transactional(rollbackFor= CustomException.class) 
	public ResponseItem increaseUserBalance(String u_id, int u_balance) throws CustomException{
		ResponseItem item = new ResponseItem();
		if(userDao.increaseUserBalance(u_id, u_balance) == 0 || u_balance > 300000) {
			throw new CustomException("418", "Fail : Reservation Exchange Error");
		}
		return item;
	}
	
	@Transactional(rollbackFor= CustomException.class)
	public void increaseAdminBalance(int a_balance) throws CustomException {
		if(userDao.increaseAdminBalance(a_balance) == 0) {
			throw new CustomException("423", "Fail : Admin Increase Balance Error");
		}
	}
	
	@Transactional(rollbackFor= CustomException.class)
	public void decreaseAdminBalance(int a_balance) throws CustomException {
		int adminBalance = userDao.getAdminBalance();
		if(adminBalance < a_balance) {
			throw new CustomException("425", "Fail : Admin Low Balance");
		}
		if(userDao.decreaseAdminBalance(a_balance) == 0) {
			throw new CustomException("424", "Fail : Admin Decrease Balance Error");
		}
	}
	
	public AdministratorDto getAdministrator() {
		return userDao.getAdministrator();
	}
	
}
