package com.tmon.platform.api.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmon.platform.api.dao.HolidayDao;
import com.tmon.platform.api.dto.HolidayDto;

/**
 * HolidayDaoImpl
 * 
 * @author 구도원
 *
 */
@Repository
public class HolidayDaoImpl implements HolidayDao {

	@Autowired
	SqlSession sqlSession;

	@Override
	public int insert(HolidayDto holidayDto) {
		return sqlSession.insert("HolidayMapper.insert", holidayDto);
	}

	@Override
	public int update(HolidayDto holidayDto) {
		return sqlSession.update("HolidayMapper.update", holidayDto);
	}

	@Override
	public int delete(HolidayDto holidayDto) {
		return sqlSession.delete("HolidayMapper.delete", holidayDto);
	}

	@Override
	public List<HolidayDto> select() {
		return sqlSession.selectList("HolidayMapper.select");
	}

}
