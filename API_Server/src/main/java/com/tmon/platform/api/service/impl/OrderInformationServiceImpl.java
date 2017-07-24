package com.tmon.platform.api.service.impl;

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

import com.tmon.platform.api.dao.OrderInformationDao;
import com.tmon.platform.api.dto.OrderInformationDto;
import com.tmon.platform.api.exception.OrderInformationException;
import com.tmon.platform.api.service.OrderInformationService;

/**
 * OrderInformationServiceImpl
 * 
 * @author 구도원
 * 
 *         관리자만 접근 가능한 TimeSlot Service implement
 *
 */
@Service
public class OrderInformationServiceImpl implements OrderInformationService {

	private static final Logger logger = LoggerFactory.getLogger(OrderInformationServiceImpl.class);

	@Autowired
	OrderInformationDao orderInformationDao;

	@Autowired
	SimpleDateFormat dateFormat;

	private final String datePatter = "test";

	@Override
	public List<OrderInformationDto> selectByDate(String search_init_date, String search_finish_date)
			throws OrderInformationException {

		// WHERE절 변수 입력을 위해 parameterType을 Map구조로 한다.
		Map<String, Object> betweenDate = new HashMap<String, Object>();

		try {
			betweenDate.put("search_init_date", dateFormat.parse(search_init_date));
			betweenDate.put("search_finish_date", dateFormat.parse(search_finish_date));
		} catch (ParseException e) {
			logger.info("Erro at OrderInformation selectByDate");
			throw new OrderInformationException(500, "incorrect input Date data");
		}

		// 검색 시작 날짜가 검색 끝 날짜 보다 작아야 한다.
		long initDate = ((Date) betweenDate.get("search_init_date")).getTime();
		long finishDate = ((Date) betweenDate.get("search_finish_date")).getTime();
		if (initDate > finishDate) {
			logger.info("search_start_time: " + betweenDate.get("search_init_date"));
			logger.info("search_end_time: " + betweenDate.get("search_finish_date"));
			throw new OrderInformationException(500, "search_init_date must be smaller than search_finish_date");
		}

		try {
			// Dao에서는 Map객체를 parameter로 받는다.
			return orderInformationDao.selectByDate(betweenDate);
		} catch (Exception e) {
			// OrderInformation Select 실패
			logger.error(e.toString());
			throw new OrderInformationException(500, "OrderInformation selectByDate Error");
		}

	}

	@Override
	public List<OrderInformationDto> selectByDateAndUser(String search_init_date, String search_finish_date,
			String user_id) throws OrderInformationException {
		// WHERE절 변수 입력을 위해 parameterType을 Map구조로 한다.
		Map<String, Object> paramValues = new HashMap<String, Object>();

		try {
			paramValues.put("search_init_date", dateFormat.parse(search_init_date));
			paramValues.put("search_finish_date", dateFormat.parse(search_finish_date));
			paramValues.put("user_id", user_id);

		} catch (ParseException e) {
			logger.info("Erro at OrderInformation selectByDate");
			throw new OrderInformationException(500, "incorrect input Date data");
		}

		// 검색 시작 날짜가 검색 끝 날짜 보다 작아야 한다.
		long initDate = ((Date) paramValues.get("search_init_date")).getTime();
		long finishDate = ((Date) paramValues.get("search_finish_date")).getTime();
		if (initDate > finishDate) {
			logger.info("search_start_time: " + paramValues.get("search_init_date"));
			logger.info("search_end_time: " + paramValues.get("search_finish_date"));
			throw new OrderInformationException(500, "search_init_date must be smaller than search_finish_date");
		}

		try {
			// Dao에서는 Map객체를 parameter로 받는다.
			return orderInformationDao.selectByDateAndUser(paramValues);
		} catch (Exception e) {
			// OrderInformation Select 실패
			logger.error(e.toString());
			throw new OrderInformationException(500, "OrderInformation selectByDate Error");
		}
	}

}
