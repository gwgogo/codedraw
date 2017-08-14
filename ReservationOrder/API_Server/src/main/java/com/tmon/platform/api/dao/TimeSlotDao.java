package com.tmon.platform.api.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tmon.platform.api.dto.TimeSlotInformationDto;

/**
 * TimeSlotDao
 * 
 * @author 구도원
 *
 *         관리자만 접근 가능한 Data Access Object interface
 * 
 */
public interface TimeSlotDao {

	public List<TimeSlotInformationDto> selectValid(Map<String, Object> validDate);

	public List<TimeSlotInformationDto> selectBydeliveryDate(Map<String, Date> betweenDate);
}
