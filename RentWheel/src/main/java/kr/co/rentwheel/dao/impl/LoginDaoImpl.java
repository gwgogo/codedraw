package kr.co.rentwheel.dao.impl;



import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.rentwheel.dao.LoginDao;
import kr.co.rentwheel.dto.UserDto;
import kr.co.rentwheel.mapper.UserMapper;


@Repository
public class LoginDaoImpl implements LoginDao{
	
	@Autowired 
	private SqlSession sqlSession;
	
	static Logger log = Logger.getLogger(LoginDaoImpl.class);
	
	public UserDto login(String u_id){
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		UserDto user = userMapper.login(u_id);
		if(user == null){
			log.info("LoginDao Error : Not exist ID - " + u_id);
			return null;
		}
		return user;		
	}
	
	public boolean isExistId(String u_id){
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		UserDto user = userMapper.login(u_id);	// login할 때와 같은 쿼리를 날리므로 같이 사용함
		if(user == null)
			return false;
		return true;
	}
	
	public boolean userJoin(UserDto user){
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		try{
			userMapper.userJoin(user);
		}catch(Exception e){
			log.info("Join Insert error : " + e);
			return false;
		}
		return true;
	}
}
