package com.tmon.platform.api.service;

import java.util.List;
import java.util.Map;

import com.tmon.platform.api.dto.TimeSlotSettingDto;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.SQLCustomException;

/**
 * TimeSlotSettingService
 * 
 * @author 구도원
 *
 */
public interface TimeSlotSettingService {

	public Map<String, String> insert(String startTime, String endTime, String cutoffTime)
			throws DateFormatException, SQLCustomException;

	public Map<String, String> update(int timeslotSettingID, String startTime, String endTime, String cutoff)
			throws DateFormatException, SQLCustomException, NullCustomException;

	public Map<String, String> delete(int timeslotSettingID)
			throws DateFormatException, SQLCustomException, NullCustomException;

	public List<TimeSlotSettingDto> select() throws SQLCustomException;
}
