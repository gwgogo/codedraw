package com.tmon.platform.api.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.TimeSlotDao;
import com.tmon.platform.api.dto.TimeSlotInformationDto;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.service.TimeSlotService;
import com.tmon.platform.api.util.UtilForDate;

/**
 * TimeSlotServiceImpl
 * 
 * @author 구도원 관리자만 접근 가능한 TimeSlot Service implement
 */
@Service
public class TimeSlotServiceImpl implements TimeSlotService {

	private static final Logger logger = LoggerFactory.getLogger(TimeSlotServiceImpl.class);

	@Autowired
	TimeSlotDao timeSlotDao;

	@Override
	public List<TimeSlotInformationDto> selectValid(String searchInitDate)
			throws DateFormatException, SQLCustomException {

		/*
		 * 검색할 날짜의 입력이 바르게 입력되었는지 확인한 후 String을 Date타입으로 변환한다.
		 * 
		 * 'validDate' Map 객체에 Object를 저장한다.
		 * 
		 * 검색 날짜로 부터 유효일자 검색은 3일로 정한다.
		 */
		Date convertedSearchInitDate = UtilForDate.convertToDate(searchInitDate, "yyyy-MM-dd");

		// WHERE절 변수 입력을 위해 parameteryType을 Map구조로 한다.
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("searchInitDate", convertedSearchInitDate);
		parameters.put("validDays", 3);

		try {
			return timeSlotDao.selectValid(parameters);
		} catch (Exception e) {
			throw new SQLCustomException(618, "Fail Select TimeSlot SQL Error, TimeSlot selectValid Error");
		}
	}

	@Override
	public List<TimeSlotInformationDto> selectBydeliveryDate(String searchInitDate, String searchFinishDate)
			throws DateFormatException, SQLCustomException {
		/*
		 * 검색할 날짜의 입력이 바르게 입력되었는지 확인한 후 String을 Date타입으로 변환한다.
		 * 
		 * 'betweenDate' Map 객체에 data를 저장한다.
		 */
		Date convertedSearchInitTime = UtilForDate.convertToDate(searchInitDate, "yyyy-MM-dd");
		Date convertedSearchFinishTime = UtilForDate.convertToDate(searchFinishDate, "yyyy-MM-dd");

		// WHERE절 변수 입력을 위해 parameterType을 Map구조로 한다.
		Map<String, Date> parameters = new HashMap<String, Date>();
		parameters.put("searchInitDate", convertedSearchInitTime);
		parameters.put("searchFinishDate", convertedSearchFinishTime);

		validateDate(parameters);

		try {
			return timeSlotDao.selectBydeliveryDate(parameters);
		} catch (Exception e) {
			throw new SQLCustomException(618, "Fail Select TimeSlot SQL Error, TimeSlot selectBydeliveryDate Error");
		}
	}

	/* Service로직을 수행할지 안할지 결정하는 메소드 */
	private void validateDate(Map<String, Date> parameters) throws DateFormatException {

		/* 시간데이터를 long타입으로 변경한다. */
		long searchInitDate = parameters.get("searchInitDate").getTime();
		long searchFinishDate = parameters.get("searchFinishDate").getTime();

		/* 검색 시작 날짜가 끝 날짜 보다 작아야 한다. */
		int flag = UtilForDate.compareDate(searchInitDate, searchFinishDate);
		if (flag < 0) {
			logger.info("searchInitDate: " + parameters.get("searchInitDate"));
			logger.info("searchFinishDate: " + parameters.get("searchFinishDate"));
			throw new DateFormatException(616, "searchInitDate must be smaller than searchFinishDate");
		}
	}
}
