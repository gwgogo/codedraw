package com.tmon.platform.api.service;

import java.util.List;

import com.tmon.platform.api.dto.HolidayDto;

public interface HolidayService {

	public List<HolidayDto> select();

	public List<HolidayDto> selectThisYear(int year);
}
