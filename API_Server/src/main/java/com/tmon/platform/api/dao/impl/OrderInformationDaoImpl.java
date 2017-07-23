package com.tmon.platform.api.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmon.platform.api.dao.OrderInformationDao;
import com.tmon.platform.api.dto.OrderInformationDto;

/**
 * OrderInformationDaoImpl
 * 
 * @author 구도원
 * 
 *         관리자만 접근 가능한 Data Access Object Implement
 *
 */
@Repository
public class OrderInformationDaoImpl implements OrderInformationDao {

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<OrderInformationDto> selectByDate(Map<String, Object> betweenDate) {
		return sqlSession.selectList("OrderInformationMapper.selectByDate", betweenDate);
	}

	@Override
	public List<OrderInformationDto> selectByDateAndUser(Map<String, Object> paramValues) {
		return sqlSession.selectList("OrderInformationMapper.selectByDateAndUser", paramValues);
	}

}
