package com.tmon.platform.api.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.tmon.platform.api.dto.HolidayDto;
import com.tmon.platform.api.exception.SQLCustomException;

/**
 * HolidayService
 * 
 * @author 구도원
 *
 */
public interface HolidayService {

	public Map<String, String> insert(int holidayLunar, String holidayDate, String holidayTitle)
			throws SQLCustomException;

	public Map<String, String> update(int holidayLunar, String holidayDate, String holidayTitle, int holidayID)
			throws SQLCustomException;

	public Map<String, String> delete(int holidayID) throws SQLCustomException;

	public List<HolidayDto> select() throws SQLCustomException;

	public List<HolidayDto> selectBythisYear(int year) throws ParseException, SQLCustomException;
}
