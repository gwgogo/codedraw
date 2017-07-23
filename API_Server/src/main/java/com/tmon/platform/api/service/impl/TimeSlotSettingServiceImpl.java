package com.tmon.platform.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.TimeSlotSettingDao;
import com.tmon.platform.api.dto.TimeSlotSettingDto;
import com.tmon.platform.api.exception.TimeSlotSettingException;
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
	SimpleDateFormat timeFormat;

	private final String timePattern = "^([0-9]|[0-1][0-9]|2[0-3]):([0-9]|[0-5][0-9]):(0|00)$";

	@Override
	public Map<String, String> insert(String start_time, String end_time)
			throws TimeSlotSettingException, ParseException {

		// 패턴 검사진행
		if (!Pattern.matches(timePattern, start_time)) {
			// 시작 시간이 잘못 입력되는 경우 TimeSlotSettingException 발생
			logger.error("시작 시간 입력 양식과 맞지 않습니다. [HH:mm:ss]");
			throw new TimeSlotSettingException(500, "시작 시간 입력 양식과 맞지 않습니다. [HH:mm:ss]");
		}
		if (!Pattern.matches(timePattern, end_time)) {
			// 끝 시간이 잘못 입력되는 경우 TimeSlotSettingException 발생
			logger.error("끝 시간 입력 양식과 맞지 않습니다. [HH:mm:ss]");
			throw new TimeSlotSettingException(500, "끝 시간 입력 양식과 맞지 않습니다. [HH:mm:ss]");
		}

		// 시작시간이 끝시간 보다 작아야 한다.
		if (timeFormat.parse(start_time).getTime() >= timeFormat.parse(end_time).getTime()) {
			logger.info("start_time: " + start_time);
			logger.info("end_time: " + end_time);
			throw new TimeSlotSettingException(500, "start_time must be smaller than end_time");
		}

		// 'timeSlotSettingDto'객체의 setter를 이용하여 변수에 data를 저장한다.
		TimeSlotSettingDto timeSlotSettingDto = new TimeSlotSettingDto();
		timeSlotSettingDto.setStart_time(start_time);
		timeSlotSettingDto.setEnd_time(end_time);

		Map<String, String> out = new HashMap<String, String>();
		try {
			// TimeSlotSetting Insert 성공
			timeSlotSettingDao.insert(timeSlotSettingDto);
			out.put("msg", "Success Insert TimeSlot");
		} catch (Exception e) {
			// TimeSlotSetting Insert 실패
			throw new TimeSlotSettingException(500, "TimeSlotSetting Insert Error");
		}

		return out;
	}

	@Override
	public Map<String, String> update(int timeslot_setting_id, String start_time, String end_time)
			throws TimeSlotSettingException, ParseException {

		// 패턴 검사진행
		if (!Pattern.matches(timePattern, start_time)) {
			// 시작 시간이 잘못 입력되는 경우 TimeSlotSettingException 발생
			logger.error("시작 시간 입력 양식과 맞지 않습니다. [HH:mm:ss]");
			throw new TimeSlotSettingException(500, "시작 시간 입력 양식과 맞지 않습니다. [HH:mm:ss]");
		}
		if (!Pattern.matches(timePattern, end_time)) {
			// 끝 시간이 잘못 입력되는 경우 TimeSlotSettingException 발생
			logger.error("끝 시간 입력 양식과 맞지 않습니다. [HH:mm:ss]");
			throw new TimeSlotSettingException(500, "끝 시간 입력 양식과 맞지 않습니다. [HH:mm:ss]");
		}

		// 시작시간이 끝시간 보다 작아야 한다.
		if (timeFormat.parse(start_time).getTime() >= timeFormat.parse(end_time).getTime()) {
			logger.info("start_time: " + start_time);
			logger.info("end_time: " + end_time);
			throw new TimeSlotSettingException(500, "start_time must be smaller than end_time");
		}

		TimeSlotSettingDto timeSlotSettingDto = new TimeSlotSettingDto();
		// 'timeSlotSettingDto'객체의 setter를 이용하여 변수에 data를 저장한다.
		timeSlotSettingDto.setStart_time(start_time);
		timeSlotSettingDto.setEnd_time(end_time);
		timeSlotSettingDto.setTimeslot_setting_id(timeslot_setting_id);

		Map<String, String> out = new HashMap<String, String>();
		try {
			// TimeSlotSetting Insert 성공
			timeSlotSettingDao.update(timeSlotSettingDto);
			out.put("msg", "Success Update TimeSlot");
		} catch (Exception e) {
			// TimeSlotSetting Update 실패
			throw new TimeSlotSettingException(500, "TimeSlotSetting Update Error");
		}

		return out;
	}

	@Override
	public Map<String, String> delete(int timeslot_setting_id) throws TimeSlotSettingException {

		TimeSlotSettingDto timeSlotSettingDto = new TimeSlotSettingDto();

		// 삭제할 타임슬롯 시간대의 고유번호
		timeSlotSettingDto.setTimeslot_setting_id(timeslot_setting_id);

		Map<String, String> out = new HashMap<String, String>();
		try {
			// TimeSlotSetting Delete 성공
			timeSlotSettingDao.delete(timeSlotSettingDto);
			out.put("msg", "Success Delete TimeSlot");
		} catch (Exception e) {
			// TimeSlotSetting Delete 실패
			throw new TimeSlotSettingException(500, "TimeSlotSetting Delete Error");
		}
		return out;
	}

	@Override
	public List<TimeSlotSettingDto> select() {
		return timeSlotSettingDao.select();
	}

}
