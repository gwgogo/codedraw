package com.tmon.platform.api.service;

import java.util.List;
import java.util.Map;

import com.tmon.platform.api.dto.HolidayDto;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.exception.StopServiceException;

/**
 * HolidayService
 * 
 * @author 구도원
 *
 */
public interface HolidayService {

	public Map<String, String> insert(String holidayDate, String holidayTitle)
			throws SQLCustomException, DateFormatException, StopServiceException;

	public Map<String, String> update(String holidayDate, String holidayTitle, int holidayID)
			throws SQLCustomException, DateFormatException, StopServiceException, NullCustomException;

	public Map<String, String> delete(int holidayID)
			throws SQLCustomException, StopServiceException, NullCustomException;

	public List<HolidayDto> selectBythisYear(int year) throws SQLCustomException;
}
