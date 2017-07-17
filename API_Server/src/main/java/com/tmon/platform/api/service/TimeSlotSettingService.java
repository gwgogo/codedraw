package com.tmon.platform.api.service;

import java.util.List;

import com.tmon.platform.api.dto.TimeSlotSettingDto;

/**
 * TimeSlotSettingService
 * 
 * @author 구도원
 *
 */
public interface TimeSlotSettingService {

	public int insert(TimeSlotSettingDto timeSlotSettingDto);

	public int update(TimeSlotSettingDto timeSlotSettingDto);

	public int delete(TimeSlotSettingDto timeSlotSettingDto);

	public List<TimeSlotSettingDto> select();
}
