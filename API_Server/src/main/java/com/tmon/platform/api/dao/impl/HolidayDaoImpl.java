package com.tmon.platform.api.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmon.platform.api.dao.HolidayDao;
import com.tmon.platform.api.dto.HolidayDto;

@Repository
public class HolidayDaoImpl implements HolidayDao {

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<HolidayDto> select() {
		return sqlSession.selectList("HolidayMapper.select");
	}

}
