package com.tmon.platform.api.service;

import java.util.List;
import java.util.Map;

import com.tmon.platform.api.dto.TimeSlotDto;
import com.tmon.platform.api.exception.TimeSlotException;

/**
 * TimeSlotService
 * 
 * @author 구도원
 * 
 *         관리자만 접근 가능한 TimeSlot Service interface
 *
 */
public interface TimeSlotService {

	public Map<String, String> insert(String start_time, String end_time, String delivery_date, int count)
			throws TimeSlotException;

	public Map<String, String> update(String start_time, String end_time, int timeslot_id) throws TimeSlotException;

	public Map<String, String> delete(int timeslot_id) throws TimeSlotException;

	public List<TimeSlotDto> selectBydelivery_date(String search_init_date, String search_finish_date) throws TimeSlotException;

}
