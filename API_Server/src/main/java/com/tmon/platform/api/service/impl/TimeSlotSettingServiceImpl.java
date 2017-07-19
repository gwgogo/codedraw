package com.tmon.platform.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.TimeSlotSettingDao;
import com.tmon.platform.api.dto.TimeSlotSettingDto;
import com.tmon.platform.api.service.TimeSlotSettingService;

/**
 * TimeSlotSettingServiceImpl
 * 
 * @author 구도원
 *
 */
@Service
public class TimeSlotSettingServiceImpl implements TimeSlotSettingService {

	private static final Logger logger = LoggerFactory.getLogger(TimeSlotSettingServiceImpl.class);

	@Autowired
	TimeSlotSettingDao timeSlotSettingDao;

	@Override
	public int insert(TimeSlotSettingDto timeSlotSettingDto) {
		return timeSlotSettingDao.insert(timeSlotSettingDto);
	}

	@Override
	public int update(TimeSlotSettingDto timeSlotSettingDto) {
		return timeSlotSettingDao.update(timeSlotSettingDto);
	}

	@Override
	public int delete(TimeSlotSettingDto timeSlotSettingDto) {
		return timeSlotSettingDao.delete(timeSlotSettingDto);
	}

	@Override
	public List<TimeSlotSettingDto> select() {
		return timeSlotSettingDao.select();
	}

}
