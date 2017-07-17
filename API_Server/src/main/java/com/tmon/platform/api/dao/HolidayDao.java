package com.tmon.platform.api.dao;

import java.util.List;

import com.tmon.platform.api.dto.HolidayDto;

public interface HolidayDao {

	public List<HolidayDto> select();
}
