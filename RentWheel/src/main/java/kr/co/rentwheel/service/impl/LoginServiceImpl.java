package kr.co.rentwheel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.rentwheel.dao.LoginDao;
import kr.co.rentwheel.domain.ResponseItem;
import kr.co.rentwheel.dto.UserDto;
import kr.co.rentwheel.service.LoginService;


@Service
public class LoginServiceImpl implements LoginService{
	@Autowired
	private LoginDao loginDao;
	
	public ResponseItem login(String u_id, String u_pw){
		ResponseItem responseItem = new ResponseItem();
		UserDto user;
		
		if((user = loginDao.login(u_id)) == null){
			responseItem.setMsg("Fail : not exist ID");
			responseItem.setResult("401");
		}
		else if(!user.getU_pw().equals(u_pw)){
			responseItem.setMsg("Fail : not correct");
			responseItem.setResult("402");
		}
		return responseItem;	
	}
	
	public ResponseItem isExistId(String u_id){
		ResponseItem responseItem = new ResponseItem();
		if(loginDao.isExistId(u_id)){
			responseItem.setMsg("Fail : already exist ID");
			responseItem.setResult("405");
		}
		return responseItem;
	}
	
	public ResponseItem userJoin(UserDto user){
		ResponseItem responseItem = new ResponseItem();
		if(!loginDao.userJoin(user)){
			responseItem.setMsg("Fail : User Join Error");
			responseItem.setResult("403");
		}
		return responseItem;
	}
}
