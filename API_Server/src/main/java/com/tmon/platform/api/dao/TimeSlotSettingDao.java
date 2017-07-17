package com.tmon.platform.api.dao;

import java.util.List;

import com.tmon.platform.api.dto.TimeSlotSettingDto;

/**
 * TimeSlotSettingDao
 * 
 * @author 구도원
 *
 */
public interface TimeSlotSettingDao {

	public int insert(TimeSlotSettingDto timeSlotSettingDto);

	public int update(TimeSlotSettingDto timeSlotSettingDto);

	public int delete(TimeSlotSettingDto timeSlotSettingDto);

	public List<TimeSlotSettingDto> select();
}
