package com.tmon.platform.api.service.impl;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmon.platform.api.dao.TimeSlotSettingDao;
import com.tmon.platform.api.dto.TimeSlotSettingDto;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.service.TimeSlotSettingService;

/**
 * TimeSlotSettingServiceImpl
 * 
 * @author 구도원
 * 
 *         관리자만 접근 가능한 TimeSlot Service implement
 *
 */
@Service
public class TimeSlotSettingServiceImpl implements TimeSlotSettingService {

	private static final Logger logger = LoggerFactory.getLogger(TimeSlotSettingServiceImpl.class);

	@Autowired
	TimeSlotSettingDao timeSlotSettingDao;

	@Autowired
	SimpleDateFormat timeFormat; // 시간 데이터가 포맷에 맞게 입력되었는지 확인한다.

	@Override
	public Map<String, String> insert(String startTime, String endTime, String cutoff)
			throws DateFormatException, SQLCustomException {

		TimeSlotSettingDto timeSlotSettingDto = new TimeSlotSettingDto();
		try {
			/*
			 * 생성할 타임슬롯의 시간대(시작시간, 끝시간)의 입력이 올바르게 되었는지 확인한다. 문자열로 입력된 시간을 Time 타입으로 변환한다.
			 * 
			 * 'timeSlotSettingDto'객체의 setter를 이용하여 변수에 data를 저장한다.
			 */
			timeSlotSettingDto.setStartTime(new Time(timeFormat.parse(startTime).getTime()));
			timeSlotSettingDto.setEndTime(new Time(timeFormat.parse(endTime).getTime()));
			timeSlotSettingDto.setCutoff(new Time(timeFormat.parse(cutoff).getTime()));
		} catch (ParseException e) {
			logger.info("Error at TimeslotSetting insert");
			throw new DateFormatException(616, "Incorrect input Time data");
		}

		// 시작시간이 끝시간 보다 작아야 한다.
		if (timeSlotSettingDto.getStartTime().getTime() >= timeSlotSettingDto.getEndTime().getTime()) {
			logger.info("startTime: " + timeSlotSettingDto.getStartTime());
			logger.info("endTime: " + timeSlotSettingDto.getEndTime());
			throw new DateFormatException(616, "startTime must be smaller than endTime");
		}

		Map<String, String> out = new HashMap<String, String>();
		try {
			// TimeSlotSetting Insert 성공
			timeSlotSettingDao.insert(timeSlotSettingDto);
			out.put("msg", "Success Insert TimeSlot");
		} catch (Exception e) {
			// TimeSlotSetting Insert 실패
			throw new SQLCustomException(619, "Fail Insert TIMESLOT_SETTING SQL Error");
		}

		return out;
	}

	@Override
	public Map<String, String> update(int timeslotSettingID, String startTime, String endTime, String cutoff)
			throws DateFormatException, SQLCustomException {

		TimeSlotSettingDto timeSlotSettingDto = new TimeSlotSettingDto();
		try {
			/*
			 * 생성할 타임슬롯의 시간대(시작시간, 끝시간)의 입력이 올바르게 되었는지 확인한다. 문자열로 입력된 시간을 Time 타입으로 변환한다.
			 * 
			 * 'timeSlotSettingDto'객체의 setter를 이용하여 변수에 data를 저장한다.
			 */
			timeSlotSettingDto.setStartTime(new Time(timeFormat.parse(startTime).getTime()));
			timeSlotSettingDto.setEndTime(new Time(timeFormat.parse(endTime).getTime()));
			timeSlotSettingDto.setCutoff(new Time(timeFormat.parse(cutoff).getTime()));
			timeSlotSettingDto.setTimeslotSettingID(timeslotSettingID);
		} catch (ParseException e) {
			logger.info("Error at TimeslotSetting update");
			throw new DateFormatException(616, "Incorrect input Time data");
		}

		// 시작시간이 끝시간 보다 작아야 한다.
		if (timeSlotSettingDto.getStartTime().getTime() >= timeSlotSettingDto.getEndTime().getTime()) {
			logger.info("startTime: " + timeSlotSettingDto.getStartTime());
			logger.info("endTime: " + timeSlotSettingDto.getEndTime());
			throw new DateFormatException(616, "startTime must be smaller than endTime");
		}

		Map<String, String> out = new HashMap<String, String>();
		try {
			// TimeSlotSetting Insert 성공
			timeSlotSettingDao.update(timeSlotSettingDto);
			out.put("msg", "Success Update TimeSlot");
		} catch (Exception e) {
			// TimeSlotSetting Update 실패
			throw new SQLCustomException(619, "Fail Update TIMESLOT_SETTING SQL Error");
		}

		return out;
	}

	@Override
	public Map<String, String> delete(int timeslotSettingID) throws DateFormatException, SQLCustomException {

		TimeSlotSettingDto timeSlotSettingDto = new TimeSlotSettingDto();

		// 삭제할 타임슬롯 시간대의 고유번호
		timeSlotSettingDto.setTimeslotSettingID(timeslotSettingID);

		Map<String, String> out = new HashMap<String, String>();
		try {
			// TimeSlotSetting Delete 성공
			timeSlotSettingDao.delete(timeSlotSettingDto);
			out.put("msg", "Success Delete TimeSlot");
		} catch (Exception e) {
			// TimeSlotSetting Delete 실패
			throw new SQLCustomException(619, "Fail Delete TIMESLOT_SETTING SQL Error");
		}
		return out;
	}

	@Override
	public List<TimeSlotSettingDto> select() throws SQLCustomException {
		try {
			return timeSlotSettingDao.select();
		} catch (Exception e) {
			// TimeSlot Select 실패
			throw new SQLCustomException(619, "Fail Select TIMESLOT_SETTING SQL Error, TimeSlotSetting Select Error");
		}
	}

}
