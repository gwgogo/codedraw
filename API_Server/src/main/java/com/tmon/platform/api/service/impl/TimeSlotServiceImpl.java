package com.tmon.platform.api.service.impl;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.TimeSlotDao;
import com.tmon.platform.api.dto.TimeSlotDto;
import com.tmon.platform.api.dto.TimeSlotInformationDto;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.service.TimeSlotService;

/**
 * TimeSlotServiceImpl
 * 
 * @author 구도원
 * 
 *         관리자만 접근 가능한 TimeSlot Service implement
 *
 */
@Service
public class TimeSlotServiceImpl implements TimeSlotService {

	private static final Logger logger = LoggerFactory.getLogger(TimeSlotServiceImpl.class);

	@Autowired
	TimeSlotDao timeSlotDao;

	@Autowired
	SimpleDateFormat dateFormat; // 날짜 데이터가 포맷에 밪게 입력되었는지 확인한다.

	@Autowired
	SimpleDateFormat timeFormat; // 시간 데이터가 포맷에 맞게 입력되었는지 확인한다.

	@Override
	public Map<String, String> insert(String startTime, String endTime, String deliveryDate, int count)
			throws DateFormatException, SQLCustomException {

		TimeSlotDto timeSlotDto = new TimeSlotDto();

		try {
			/*
			 * 생성할 타임슬롯의 시간대(시작시간, 끝시간)와 날짜의 입력이 올바르게 되었는지 확인한다. 문자열로 입력된 날짜와 시간을 Date, Time
			 * 타입으로 변환한다.
			 * 
			 * 'timeSlotDto'객체의 setter를 이용하여 변수에 data를 저장한다.
			 */
			timeSlotDto.setStartTime(new Time(timeFormat.parse(startTime).getTime()));
			timeSlotDto.setEndTime(new Time(timeFormat.parse(endTime).getTime()));
			timeSlotDto.setDeliveryDate(dateFormat.parse(deliveryDate));
			timeSlotDto.setCount(count);

		} catch (ParseException e) {
			logger.info("Error at Timeslot insert");
			throw new DateFormatException(616, "Incorrect input Time or Date data");
		}

		// 시작시간이 끝시간 보다 작아야 한다.
		if (timeSlotDto.getStartTime().getTime() >= timeSlotDto.getEndTime().getTime()) {
			logger.info("startTime: " + timeSlotDto.getStartTime());
			logger.info("endTime: " + timeSlotDto.getEndTime());
			throw new DateFormatException(616, "startTime must be smaller than endTime");
		}

		Map<String, String> out = new HashMap<String, String>();
		try {
			// TimeSlot Insert 성공
			timeSlotDao.insert(timeSlotDto);
			out.put("msg", "Success Insert TimeSlot");
		} catch (Exception e) {
			// TimeSlot Insert 실패
			throw new SQLCustomException(618, "TimeSlot Insert Error");
		}

		return out;
	}

	@Override
	public Map<String, String> update(String startTime, String endTime, int timeslotID)
			throws DateFormatException, SQLCustomException {

		TimeSlotDto timeSlotDto = new TimeSlotDto();

		try {
			/*
			 * 수정할 타임슬롯의 시간대(시작시간, 끝시간)의 입력이 올바르게 되었는지 확인한다. 문자열로 입력된 날짜와 시간을 Date, Time
			 * 타입으로 변환한다.
			 * 
			 * 'timeSlotDto'객체의 setter를 이용하여 변수에 data를 저장한다.
			 */
			timeSlotDto.setStartTime(new Time(timeFormat.parse(startTime).getTime()));
			timeSlotDto.setEndTime(new Time(timeFormat.parse(endTime).getTime()));
			timeSlotDto.setTimeslotID(timeslotID);
		} catch (ParseException e) {
			logger.info("Error at Timeslot update");
			throw new DateFormatException(616, "Incorrect input Time or Date data");
		}

		// 시작시간이 끝시간 보다 작아야 한다.
		if (timeSlotDto.getStartTime().getTime() >= timeSlotDto.getEndTime().getTime()) {
			logger.info("startTime: " + timeSlotDto.getStartTime());
			logger.info("endTime: " + timeSlotDto.getEndTime());
			throw new DateFormatException(616, "startTime must be smaller than endTime");
		}

		Map<String, String> out = new HashMap<String, String>();
		try {

			// TimeSlot Update 성공
			timeSlotDao.update(timeSlotDto);
			out.put("msg", "Success Update TimeSlot");

		} catch (Exception e) {

			// TimeSlot Update 실패
			throw new SQLCustomException(618, "TimeSlot Update Error");
		}
		return out;
	}

	@Override
	public Map<String, String> delete(int timeslotID) throws SQLCustomException {

		TimeSlotDto timeSlotDto = new TimeSlotDto();

		// 삭제할 타임슬롯의 고유번호를 입력한다. 'timeSlotDto'객체의 setter를 이용하여 변수에 data를 저장한다.
		// CASCADE DELETE를 설정하지 않는다.

		timeSlotDto.setTimeslotID(timeslotID);

		Map<String, String> out = new HashMap<String, String>();
		try {
			// TimeSlot Delete 성공
			timeSlotDao.delete(timeSlotDto);
			out.put("msg", "Success Delete TimeSlot");
		} catch (Exception e) {
			// TimeSlot Delete 실패
			throw new SQLCustomException(618, "TimeSlot Delete Error");
		}
		return out;
	}

	@Override
	public List<TimeSlotInformationDto> selectValid(String searchInitDate)
			throws DateFormatException, SQLCustomException {

		// WHERE절 변수 입력을 위해 parameteryType을 Map구조로 한다.
		Map<String, Object> validDate = new HashMap<String, Object>();

		try {

			/*
			 * 검색할 날짜의 입력이 바르게 입력되었는지 확인한 후 String을 Date타입으로 변환한다.
			 * 
			 * 'validDate' Map 객체에 Object를 저장한다.
			 * 
			 * 검색 날짜로 부터 유효일자 검색은 3일로 정한다.
			 */
			validDate.put("searchInitDate", dateFormat.parse(searchInitDate));
			validDate.put("validDays", 3);

		} catch (ParseException e) {
			logger.info("Error at TimeSlot selectValid");
			throw new DateFormatException(616, "Incorrect input Date data");
		}

		try {
			// Dao에서는 Map객체를 parameter로 받는다.
			return timeSlotDao.selectValid(validDate);
		} catch (Exception e) {
			// TimeSlot Select 실패
			throw new SQLCustomException(618, "Fail Select TimeSlot SQL Error, TimeSlot selectValid Error");
		}
	}

	@Override
	public List<TimeSlotInformationDto> selectBydeliveryDate(String searchInitDate, String searchFinishDate)
			throws DateFormatException, SQLCustomException {

		// WHERE절 변수 입력을 위해 parameterType을 Map구조로 한다.
		Map<String, Date> betweenDate = new HashMap<String, Date>();

		try {

			/*
			 * 검색할 날짜의 입력이 바르게 입력되었는지 확인한 후 String을 Date타입으로 변환한다.
			 * 
			 * 'betweenDate' Map 객체에 data를 저장한다.
			 */
			betweenDate.put("searchInitDate", dateFormat.parse(searchInitDate));
			betweenDate.put("searchFinishDate", dateFormat.parse(searchFinishDate));

		} catch (ParseException e) {
			logger.info("Error at TimeSlot selectBydeliveryDate");
			throw new DateFormatException(616, "Incorrect input Date data");
		}

		// 검색 시작 날짜가 검색 끝 날짜 보다 작아야 한다.
		long initDate = ((Date) betweenDate.get("searchInitDate")).getTime();
		long finishDate = ((Date) betweenDate.get("searchFinishDate")).getTime();
		if (initDate > finishDate) {
			logger.info("searchStartTime: " + betweenDate.get("searchInitDate"));
			logger.info("searchEndTime: " + betweenDate.get("searchFinishDate"));
			throw new DateFormatException(616, "searchInitDate must be smaller than searchFinishDate");
		}

		try {
			// Dao에서는 Map객체를 parameter로 받는다.
			return timeSlotDao.selectBydeliveryDate(betweenDate);
		} catch (Exception e) {
			// TimeSlot Select 실패
			e.printStackTrace();
			throw new SQLCustomException(618, "Fail Select TimeSlot SQL Error, TimeSlot selectBydeliveryDate Error");
		}
	}
}
