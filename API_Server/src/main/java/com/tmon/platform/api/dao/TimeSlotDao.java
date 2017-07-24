package com.tmon.platform.api.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tmon.platform.api.dto.TimeSlotDto;

/**
 * TimeSlotDao
 * 
 * @author 구도원
 *
 *         관리자만 접근 가능한 Data Access Object interface
 * 
 */
public interface TimeSlotDao {

	public int insert(TimeSlotDto timeSlotDto);

	public int update(TimeSlotDto timeSlotDto);

	public int delete(TimeSlotDto timeSlotDto);

	public List<TimeSlotDto> selectBydelivery_date(Map<String, Date> betweenDate);
}
