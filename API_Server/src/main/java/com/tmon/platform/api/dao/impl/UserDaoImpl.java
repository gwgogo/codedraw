package com.tmon.platform.api.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmon.platform.api.dao.UserDao;
import com.tmon.platform.api.dto.UserDto;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public UserDto user(String userID){
		return sqlSession.selectOne("UserMapper.user", userID);
	}
	
	public UserDto login(String userID, String userPW) {
		Map<String, String> argument = new HashMap<>();
		argument.put("userID", userID);
		argument.put("userPW", userPW);
		return sqlSession.selectOne("UserMapper.login", argument);
	}
	
	public void join(UserDto userDto) {
		sqlSession.insert("UserMapper.insertUser", userDto);
	}
}
