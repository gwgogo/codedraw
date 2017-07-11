package com.tmon.platform.api.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmon.platform.api.dao.UserDao;
import com.tmon.platform.api.dto.UserDto;


@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public UserDto login(String user_id) {
		return sqlSession.selectOne("UserMapper.selectUserById", user_id);
	}
}
