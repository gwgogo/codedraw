package com.tmon.platform.api.dao;

import java.util.List;
import java.util.Map;

import com.tmon.platform.api.dto.HolidayDto;

/**
 * HolidayDao
 * 
 * @author 구도원
 *
 */
public interface HolidayDao {

	public int insert(HolidayDto holidayDto);

	public int update(HolidayDto holidayDto);

	public int delete(HolidayDto holidayDto);

	public HolidayDto selectByholidayID(Map<String, Integer> parameters);

	public List<HolidayDto> selectBythisYear(Map<String, String> parameters);
}
