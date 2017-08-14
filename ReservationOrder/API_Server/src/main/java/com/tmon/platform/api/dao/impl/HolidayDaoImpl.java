package com.tmon.platform.api.dao.impl;

import java.util.List;
import java.util.Map;

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
	public HolidayDto selectByholidayID(Map<String, Integer> parameters) {
		return sqlSession.selectOne("HolidayMapper.selectByholidayID", parameters);
	}

	@Override
	public List<HolidayDto> selectBythisYear(Map<String, String> parameters) {
		return sqlSession.selectList("HolidayMapper.selectBythisYear", parameters);
	}

}
