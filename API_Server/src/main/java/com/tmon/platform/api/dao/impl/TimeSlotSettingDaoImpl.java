package com.tmon.platform.api.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmon.platform.api.dao.TimeSlotSettingDao;
import com.tmon.platform.api.dto.TimeSlotSettingDto;

/**
 * TimeSlotSettingDaoImpl
 * 
 * @author 구도원
 *
 */
@Repository
public class TimeSlotSettingDaoImpl implements TimeSlotSettingDao {

	@Autowired
	SqlSession sqlSession;

	@Override
	public int insert(TimeSlotSettingDto timeSlotSettingDto) {
		return sqlSession.insert("TimeSlotSettingMapper.insert", timeSlotSettingDto);
	}

	@Override
	public int update(TimeSlotSettingDto timeSlotSettingDto) {
		return sqlSession.update("TimeSlotSettingMapper.update", timeSlotSettingDto);
	}

	@Override
	public int delete(TimeSlotSettingDto timeSlotSettingDto) {
		return sqlSession.delete("TimeSlotSettingMapper.delete", timeSlotSettingDto);
	}

	@Override
	public List<TimeSlotSettingDto> select() {
		return sqlSession.selectList("TimeSlotSettingMapper.select");
	}

}
