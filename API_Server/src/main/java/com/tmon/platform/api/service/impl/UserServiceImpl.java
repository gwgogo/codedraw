package com.tmon.platform.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.UserDao;
import com.tmon.platform.api.domain.ResponseItem;
import com.tmon.platform.api.dto.UserDto;
import com.tmon.platform.api.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	
	public ResponseItem login(String user_id, String user_pw) {
		ResponseItem item = new ResponseItem();
		UserDto userDto = userDao.login(user_id);
		if(!user_pw.equals(userDto.getUser_pw())) {
			item.setCode("444");
			item.setMsg("password error");
		}
		
		return item;
	}
}
