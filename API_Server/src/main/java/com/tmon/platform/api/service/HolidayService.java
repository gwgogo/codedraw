package com.tmon.platform.api.service;

import java.text.ParseException;
import java.util.List;

import com.tmon.platform.api.dto.HolidayDto;

/**
 * HolidayService
 * 
 * @author 구도원
 *
 */
public interface HolidayService {

	public int insert(int holiday_lunar, String holiday_date, String holiday_title);

	public int update(int holiday_lunar, String holiday_date, String holiday_title, int holiday_id);

	public int delete(int holiday_id);

	public List<HolidayDto> select();

	public List<HolidayDto> selectBythisYear(int year) throws ParseException;
}
