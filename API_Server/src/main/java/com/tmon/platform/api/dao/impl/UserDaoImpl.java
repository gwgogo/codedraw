package com.tmon.platform.api.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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
	
	public UserDto user(String user_id){
		return sqlSession.selectOne("UserMapper.user", user_id);
	}
	
	public UserDto login(String user_id, String user_pw) {
		Map<String, String> argument = new HashMap<>();
		argument.put("user_id", user_id);
		argument.put("user_pw", user_pw);
		return sqlSession.selectOne("UserMapper.login", argument);
	}
	
	public void join(UserDto userDto) {
		sqlSession.insert("UserMapper.insertUser", userDto);
	}
}
