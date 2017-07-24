package com.tmon.platform.api.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.tmon.platform.api.dto.TimeSlotSettingDto;
import com.tmon.platform.api.exception.TimeSlotSettingException;

/**
 * TimeSlotSettingService
 * 
 * @author 구도원
 *
 */
public interface TimeSlotSettingService {

	public Map<String, String> insert(String start_time, String end_time)
			throws TimeSlotSettingException, ParseException;

	public Map<String, String> update(int timeslot_setting_id, String start_time, String end_time)
			throws TimeSlotSettingException, ParseException;

	public Map<String, String> delete(int timeslot_setting_id) throws TimeSlotSettingException;

	public List<TimeSlotSettingDto> select();
}
