package kr.co.rentwheel.dao.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.rentwheel.dao.UserDao;
import kr.co.rentwheel.dto.UserDto;
import kr.co.rentwheel.mapper.UserMapper;


@Repository
public class UserDaoImpl implements UserDao{
	
	@Autowired 
	private SqlSession sqlSession;
	
	static Logger log = Logger.getLogger(UserDaoImpl.class);
	
	public UserDto userLogin(String u_id){
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		return userMapper.userLogin(u_id);
	}
	
	public UserDto userJoinDuplicationValidate(String u_id){
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		return userMapper.userLogin(u_id);	// login할 때와 같은 쿼리를 날리므로 같이 사용함
	}
	
	public void userJoin(UserDto user){
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		userMapper.userJoin(user);
		
	}
	
	public int getUserBalance(String u_id) {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		return userMapper.getUserBalance(u_id);
	}
	
	public List<UserDto> getUserAll(){
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		return userMapper.getUserAll();
	}
	public UserDto getUserByUserID(String u_id) {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		return userMapper.getUserByUserID(u_id);
	}
	
	public void decreaseUserBalance(String u_id, int u_balance) {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		Map<String, String> map = new HashMap();
		map.put("u_id", u_id);
		map.put("u_balance", String.valueOf(u_balance));
		userMapper.decreaseUserBalance(map);
	}
	
	public int increaseUserBalance(String u_id, int u_balance) {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		Map<String, String> map = new HashMap();
		map.put("u_id", u_id);
		map.put("u_balance", String.valueOf(u_balance));
		return userMapper.increaseUserBalance(map);
	}
	
	public int increaseAdminBalance(int a_balance) {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		return userMapper.increaseAdminBalance(a_balance);
	}
	public int decreaseAdminBalance(int a_balance) {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		return userMapper.decreaseAdminBalance(a_balance);
	}
	public int getAdminBalance() {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		return userMapper.getAdminBalance();
	}
}
