package com.tmon.platform.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmon.platform.api.dao.HolidayDao;
import com.tmon.platform.api.dto.HolidayDto;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.exception.StopServiceException;
import com.tmon.platform.api.service.HolidayService;

/**
 * HolidayServiceImpl
 * 
 * @author 구도원
 *
 */
@Service
public class HolidayServiceImpl implements HolidayService {

	@Autowired
	HolidayDao holidayDao;

	@Override
	public Map<String, String> insert(String holidayDate, String holidayTitle)
			throws SQLCustomException, DateFormatException, StopServiceException {

		/* 입력받은 데이터로 'inpuHolidayDto'를 setting 한다. */
		HolidayDto inputHolidayDto = new HolidayDto(holidayDate, holidayTitle);

		/* inputHolidayDto의 holidayTitle의 유효성 검사를 한다. */
		validateTitle(holidayTitle);

		try {
			/* Holiday Insert 성공 */
			holidayDao.insert(inputHolidayDto);
		} catch (Exception e) {
			/* Holiday Insert 실패 */
			throw new SQLCustomException(617, "Fail Insert HOLIDAY SQL Error");
		}

		Map<String, String> out = new HashMap<String, String>();
		out.put("msg", "Success Insert Holiday");
		return out;
	}

	@Override
	public Map<String, String> update(String holidayDate, String holidayTitle, int holidayID)
			throws SQLCustomException, DateFormatException, StopServiceException, NullCustomException {

		/* 입력받은 데이터로 'inpuHolidayDto'를 setting 한다. */
		HolidayDto inputHolidayDto = new HolidayDto(holidayDate, holidayTitle, holidayID);

		/* 수정하려는 공휴일의 holidayID을 검사한다. (미리 입력되어 있는 데이터를 조회, 검사한다.) */
		validateHolidayID(holidayID);

		/* inputHolidayDto의 holidayTitle의 유효성 검사를 한다. */
		validateTitle(holidayTitle);

		try {
			/* Holiday Update 성공 */
			holidayDao.update(inputHolidayDto);
		} catch (Exception e) {
			/* Holiday Update 실패 */
			throw new SQLCustomException(617, "Fail Update HOLIDAY SQL Error");
		}

		Map<String, String> out = new HashMap<String, String>();
		out.put("msg", "Success Update Holiday");
		return out;
	}

	@Override
	public Map<String, String> delete(int holidayID)
			throws SQLCustomException, StopServiceException, NullCustomException {

		/* 입력받은 데이터로 'inpuHolidayDto'를 setting 한다. */
		HolidayDto inputHolidayDto = new HolidayDto(holidayID);

		/* 삭제하려는 공휴일의 holidaySection을 검사한다. (미리 입력되어 있는 데이터를 조회, 검사한다.) */
		validateHolidayID(holidayID);

		try {
			/* Holiday Delete 성공 */
			holidayDao.delete(inputHolidayDto);
		} catch (Exception e) {
			/* Holiday Delete 실패 */
			throw new SQLCustomException(617, "Fail Delete HOLIDAY SQL Error");
		}

		Map<String, String> out = new HashMap<String, String>();
		out.put("msg", "Success Delete Holiday");
		return out;
	}

	@Override
	public List<HolidayDto> selectBythisYear(int year) throws SQLCustomException {

		List<HolidayDto> holidays = null;
		try {
			/* DB로부터 공휴일 목록을 가져온다. */
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("year", String.valueOf(year));
			parameters.put("yearAfter1", String.valueOf(year + 1));
			holidays = holidayDao.selectBythisYear(parameters);
		} catch (Exception e) {
			throw new SQLCustomException(617, "Fail Select HOLIDAY SQL Error");
		}

		return holidays;
	}

	/* holidayTitle 유효성 검사 메소드 */
	private void validateTitle(String holidayTitle) throws StopServiceException {
		/* 'holidayTitle'이 'NULL'이 아니라면 공백문자는 허용하지 않는다. */
		if (holidayTitle.trim().equals("")) {
			throw new StopServiceException(628, "Length of 'String' Type parameter must not be zero(0)");
		}
	}

	/* holidayID 유효성 검사 메소드 */
	private void validateHolidayID(int holidayID) throws NullCustomException {

		/* SQL문에서 사용된 parameter를 Map객체를 사용한다. */
		Map<String, Integer> parameters = new HashMap<String, Integer>();

		/* SQL문은 holidayID를 통해 데이터를 찾는다. */
		parameters.put("holidayID", holidayID);
		HolidayDto holidayDto = holidayDao.selectByholidayID(parameters);

		/* SELECT결과가 NULL이면(데이터 없음) NullCustomException을 발생시킨다. */
		if (holidayDto == null) {
			throw new NullCustomException(622, "There isn't HolidayDto data");
		}
	}

}