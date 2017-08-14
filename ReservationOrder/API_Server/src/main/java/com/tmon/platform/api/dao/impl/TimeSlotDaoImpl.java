package com.tmon.platform.api.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmon.platform.api.dao.TimeSlotDao;
import com.tmon.platform.api.dto.TimeSlotInformationDto;

/**
 * TimeSlotDaoImpl
 * 
 * @author 구도원
 * 
 *         관리자만 접근 가능한 Data Access Object Implement
 *
 */
@Repository
public class TimeSlotDaoImpl implements TimeSlotDao {

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<TimeSlotInformationDto> selectValid(Map<String, Object> validDate) {
		return sqlSession.selectList("TimeSlotMapper.selectValid", validDate);
	}

	@Override
	public List<TimeSlotInformationDto> selectBydeliveryDate(Map<String, Date> betweenDate) {
		return sqlSession.selectList("TimeSlotMapper.selectBydeliveryDate", betweenDate);
	}

}
