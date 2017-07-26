package com.tmon.platform.api.service;

import java.util.List;
import java.util.Map;

import com.tmon.platform.api.dto.TimeSlotSettingDto;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.SQLCustomException;

/**
 * TimeSlotSettingService
 * 
 * @author 구도원
 *
 */
public interface TimeSlotSettingService {

	public Map<String, String> insert(String start_time, String end_time)
			throws DateFormatException, SQLCustomException;

	public Map<String, String> update(int timeslot_setting_id, String start_time, String end_time)
			throws DateFormatException, SQLCustomException;

	public Map<String, String> delete(int timeslot_setting_id) throws DateFormatException, SQLCustomException;

	public List<TimeSlotSettingDto> select() throws SQLCustomException;
}
