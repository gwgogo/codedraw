package com.tmon.platform.api.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmon.platform.api.dao.TimeSlotDao;
import com.tmon.platform.api.dto.TimeSlotDto;

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
	public int insert(TimeSlotDto timeSlotDto) {
		return sqlSession.insert("TimeSlotMapper.insert", timeSlotDto);
	}

	@Override
	public int update(TimeSlotDto timeSlotDto) {
		return sqlSession.insert("TimeSlotMapper.update", timeSlotDto);
	}

	@Override
	public int delete(TimeSlotDto timeSlotDto) {
		return sqlSession.delete("TimeSlotMapper.delete", timeSlotDto);
	}

	@Override
	public List<TimeSlotDto> selectBydelivery_date(Map<String, Date> betweenDate) {
		return sqlSession.selectList("TimeSlotMapper.selectBydelivery_date", betweenDate);
	}

}
