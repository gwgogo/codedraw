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
import com.tmon.platform.api.exception.TimeSlotException;
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
	public Map<String, String> insert(String start_time, String end_time, String delivery_date, int count)
			throws TimeSlotException {

		TimeSlotDto timeSlotDto = new TimeSlotDto();

		try {

			/*
			 * 생성할 타임슬롯의 시간대(시작시간, 끝시간)와 날짜의 입력이 올바르게 되었는지 확인한다. 문자열로 입력된 날짜와 시간을 Date 타입으로
			 * 변환한다.
			 * 
			 * 'timeSlotDto'객체의 setter를 이용하여 변수에 data를 저장한다.
			 */
			timeSlotDto.setStart_time((Time) timeFormat.parse(start_time));
			timeSlotDto.setEnd_time((Time) timeFormat.parse(end_time));
			timeSlotDto.setDelivery_date(dateFormat.parse(delivery_date));
			timeSlotDto.setCount(count);

		} catch (ParseException e) {
			logger.info("Error at Timeslot insert");
			throw new TimeSlotException(500, "incorrect input Time or Date data");
		}

		// 시작시간이 끝시간 보다 작아야 한다.
		if (timeSlotDto.getStart_time().getTime() >= timeSlotDto.getEnd_time().getTime()) {
			logger.info("start_time: " + timeSlotDto.getStart_time());
			logger.info("end_time: " + timeSlotDto.getEnd_time());
			throw new TimeSlotException(500, "start_time must be smaller than end_time");
		}

		Map<String, String> out = new HashMap<String, String>();
		try {
			// TimeSlot Insert 성공
			timeSlotDao.insert(timeSlotDto);
			out.put("msg", "Success Insert TimeSlot");
		} catch (Exception e) {
			// TimeSlot Insert 실패
			throw new TimeSlotException(500, "TimeSlot Insert Error");
		}

		return out;
	}

	@Override
	public Map<String, String> update(String start_time, String end_time, int timeslot_id) throws TimeSlotException {

		TimeSlotDto timeSlotDto = new TimeSlotDto();

		try {
			/*
			 * 수정할 타임슬롯의 시간대(시작시간, 끝시간)의 입력이 올바르게 되었는지 확인한다. 문자열로 입력된 날짜와 시간을 Date 타입으로
			 * 변환한다.
			 * 
			 * 'timeSlotDto'객체의 setter를 이용하여 변수에 data를 저장한다.
			 */
			timeSlotDto.setStart_time((Time) timeFormat.parse(start_time));
			timeSlotDto.setEnd_time((Time) timeFormat.parse(end_time));
			timeSlotDto.setTimeslot_id(timeslot_id);
		} catch (ParseException e) {
			logger.info("Error at Timeslot update");
			throw new TimeSlotException(500, "incorrect input Time or Date data");
		}

		// 시작시간이 끝시간 보다 작아야 한다.
		if (timeSlotDto.getStart_time().getTime() >= timeSlotDto.getEnd_time().getTime()) {
			logger.info("start_time: " + timeSlotDto.getStart_time());
			logger.info("end_time: " + timeSlotDto.getEnd_time());
			throw new TimeSlotException(500, "start_time must be smaller than end_time");
		}

		Map<String, String> out = new HashMap<String, String>();
		try {

			// TimeSlot Update 성공
			timeSlotDao.update(timeSlotDto);
			out.put("msg", "Success Update TimeSlot");

		} catch (Exception e) {

			// TimeSlot Update 실패
			throw new TimeSlotException(500, "TimeSlot Update Error");
		}
		return out;
	}

	@Override
	public Map<String, String> delete(int timeslot_id) throws TimeSlotException {

		TimeSlotDto timeSlotDto = new TimeSlotDto();

		// 삭제할 타임슬롯의 고유번호를 입력한다. 'timeSlotDto'객체의 setter를 이용하여 변수에 data를 저장한다.
		// CASCADE DELETE를 설정하지 않는다.

		timeSlotDto.setTimeslot_id(timeslot_id);

		Map<String, String> out = new HashMap<String, String>();
		try {
			// TimeSlot Delete 성공
			timeSlotDao.delete(timeSlotDto);
			out.put("msg", "Success Delete TimeSlot");
		} catch (Exception e) {
			// TimeSlot Delete 실패
			throw new TimeSlotException(500, "TimeSlot Delete Error");
		}
		return out;
	}

	@Override
	public List<TimeSlotDto> selectBydelivery_date(String search_init_date, String search_finish_date)
			throws TimeSlotException {

		// WHERE절 변수 입력을 위해 parameterType을 Map구조로 한다.
		Map<String, Date> betweenDate = new HashMap<String, Date>();

		try {

			/*
			 * 검색할 날짜의 입력이 바르게 입력되었는지 확인한 후 String을 Date타입으로 변환한다.
			 * 
			 * 'betweenDate' Map 객체에 data를 저장한다.
			 */
			betweenDate.put("search_init_date", dateFormat.parse(search_init_date));
			betweenDate.put("search_finish_date", dateFormat.parse(search_finish_date));

		} catch (ParseException e) {
			logger.info("Error at TimeSlot selectBydelivery_date");
			throw new TimeSlotException(500, "incorrect input Date data");
		}

		// 검색 시작 날짜가 검색 끝 날짜 보다 작아야 한다.
		long initDate = ((Date) betweenDate.get("search_init_date")).getTime();
		long finishDate = ((Date) betweenDate.get("search_finish_date")).getTime();
		if (initDate > finishDate) {
			logger.info("search_start_time: " + betweenDate.get("search_init_date"));
			logger.info("search_end_time: " + betweenDate.get("search_finish_date"));
			throw new TimeSlotException(500, "search_init_date must be smaller than search_finish_date");
		}

		try {
			// Dao에서는 Map객체를 parameter로 받는다.
			return timeSlotDao.selectBydelivery_date(betweenDate);
		} catch (Exception e) {
			// TimeSlot Select 실패
			throw new TimeSlotException(500, "TimeSlot selectBydelivery_date Error");
		}
	}

}
