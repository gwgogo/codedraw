package kr.co.rentwheel.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.rentwheel.dao.ProvisionDao;
import kr.co.rentwheel.mapper.ProvisionMapper;

@Repository
public class ProvisionDaoImpl implements ProvisionDao{

	@Autowired
	private SqlSession sqlSession;
	
	public String getProvision(int p_flag) {
		ProvisionMapper mapper = sqlSession.getMapper(ProvisionMapper.class);
		return mapper.getProvision(p_flag);
	}
	
}
