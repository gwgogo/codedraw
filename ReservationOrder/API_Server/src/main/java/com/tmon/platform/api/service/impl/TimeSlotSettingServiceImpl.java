package com.tmon.platform.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.TimeSlotSettingDao;
import com.tmon.platform.api.dto.TimeSlotSettingDto;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.service.TimeSlotSettingService;
import com.tmon.platform.api.util.UtilForDate;

/**
 * TimeSlotSettingServiceImpl
 * 
 * @author 구도원
 * 
 *         관리자만 접근 가능한 TimeSlot Service implement
 */
@Service
public class TimeSlotSettingServiceImpl implements TimeSlotSettingService {

	private static final Logger logger = LoggerFactory.getLogger(TimeSlotSettingServiceImpl.class);

	@Autowired
	TimeSlotSettingDao timeSlotSettingDao;

	@Override
	public Map<String, String> insert(String startTime, String endTime, String cutoff)
			throws DateFormatException, SQLCustomException {

		/* 입력받은 데이터로 'inputTimeSlotSettingDto'를 setting 한다. */
		TimeSlotSettingDto inputTimeSlotSettingDto = new TimeSlotSettingDto(startTime, endTime, cutoff);

		/* 입력된 inputTimeSlotSettingDto의 SQL INSERT 작업을 할지 결정한다. */
		validateTime(inputTimeSlotSettingDto);

		try {
			/* TimeSlotSetting Insert 성공 */
			timeSlotSettingDao.insert(inputTimeSlotSettingDto);

		} catch (Exception e) {
			/* TimeSlotSetting Insert 실패 */
			throw new SQLCustomException(619, "Fail Insert TIMESLOT_SETTING SQL Error");
		}

		Map<String, String> out = new HashMap<String, String>();
		out.put("msg", "Success Insert TimeSlotSetting");
		return out;
	}

	@Override
	public Map<String, String> update(int timeslotSettingID, String startTime, String endTime, String cutoff)
			throws DateFormatException, SQLCustomException, NullCustomException {

		/* 입력받은 데이터로 'inputTimeSlotSettingDto'를 setting 한다. */
		TimeSlotSettingDto inputTimeSlotSettingDto = new TimeSlotSettingDto(timeslotSettingID, startTime, endTime,
				cutoff);

		/*
		 * 1. 수정하려는 데이터의 timeslotSettingID를 근거로 하여 찾은 뒤 수정할 대상 data가 있는지 확인한다. 없는 경우,
		 * NullCustomException 발생
		 */
		validateTimeslotSettingID(timeslotSettingID);

		/* 2. 입력된 inputHoliday의 SQL UPDATE 작업을 할지 결정한다. */
		validateTime(inputTimeSlotSettingDto);

		try {
			/* TimeSlotSetting Insert 성공 */
			timeSlotSettingDao.update(inputTimeSlotSettingDto);
		} catch (Exception e) {
			/* TimeSlotSetting Update 실패 */
			throw new SQLCustomException(619, "Fail Update TIMESLOT_SETTING SQL Error");
		}

		Map<String, String> out = new HashMap<String, String>();
		out.put("msg", "Success Update TimeSlotSetting");
		return out;
	}

	@Override
	public Map<String, String> delete(int timeslotSettingID)
			throws DateFormatException, SQLCustomException, NullCustomException {

		/* 입력받은 데이터로 'inputTimeSlotSettingDto'를 setting 한다. */
		TimeSlotSettingDto timeSlotSettingDto = new TimeSlotSettingDto(timeslotSettingID);

		/*
		 * 삭제하려는 데이터의 timeslotSettingID를 근거로 하여 찾은 뒤 삭제할 대상 data가 있는지 확인한다. 없는 경우,
		 * NullCustomException 발생
		 */
		validateTimeslotSettingID(timeslotSettingID);

		try {
			/* TimeSlotSetting Delete 성공 */
			timeSlotSettingDao.delete(timeSlotSettingDto);
		} catch (Exception e) {
			/* TimeSlotSetting Delete 실패 */
			throw new SQLCustomException(619, "Fail Delete TIMESLOT_SETTING SQL Error");
		}

		Map<String, String> out = new HashMap<String, String>();
		out.put("msg", "Success Delete TimeSlotSetting");
		return out;
	}

	@Override
	public List<TimeSlotSettingDto> select() throws SQLCustomException {
		try {
			/* TimeSlotSetting Select 성공 */
			return timeSlotSettingDao.select();
		} catch (Exception e) {
			/* TimeSlotSetting Select 실패 */
			throw new SQLCustomException(619, "Fail Select TIMESLOT_SETTING SQL Error, TimeSlotSetting Select Error");
		}
	}

	/* Service로직을 수행할지 안할지 결정하는 메소드 */
	private void validateTime(TimeSlotSettingDto timeSlotSettingDto) throws DateFormatException {

		/* 시간데이터를 long타입으로 변경한다. */
		long startTime = timeSlotSettingDto.getStartTime().getTime();
		long endTime = timeSlotSettingDto.getEndTime().getTime();

		/* 시작시간이 끝시간 보다 작아야 한다. */
		int flag = UtilForDate.compareDate(startTime, endTime);
		if (flag < 1) {
			logger.info("startTime: " + timeSlotSettingDto.getStartTime());
			logger.info("endTime: " + timeSlotSettingDto.getEndTime());
			throw new DateFormatException(616, "startTime must be smaller than endTime");
		}
	}

	/* timeslotSettingID로 timeSlot */
	private void validateTimeslotSettingID(int timeslotSettingID) throws NullCustomException {

		/* SQL문에서 사용된 parameter를 Map객체를 사용한다. */
		Map<String, Integer> parameters = new HashMap<String, Integer>();

		/* SQL문은 timeslotSettingID를 통해 데이터를 찾는다. */
		parameters.put("timeslotSettingID", timeslotSettingID);
		TimeSlotSettingDto timeSlotSettingDto = timeSlotSettingDao.selectBytimeSlotSettingID(parameters);

		/* SELECT결과가 NULL이면(데이터 없음) NullCustomException을 발생시킨다. */
		if (timeSlotSettingDto == null) {
			throw new NullCustomException(624, "There isn't TimeSlotSettingDto data");
		}
	}

}
