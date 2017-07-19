package com.tmon.platform.api.service;

import java.util.List;

import com.tmon.platform.api.dto.HolidayDto;

/**
 * HolidayService
 * 
 * @author 구도원
 *
 */
public interface HolidayService {

	public int insert(HolidayDto holidayDto);

	public int update(HolidayDto holidayDto);

	public int delete(HolidayDto holidayDto);

	public List<HolidayDto> select();

	public List<HolidayDto> selectBythisYear(int year);
}
