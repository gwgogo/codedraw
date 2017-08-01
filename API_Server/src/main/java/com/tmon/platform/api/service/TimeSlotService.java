package com.tmon.platform.api.service;

import java.util.List;
import java.util.Map;

import com.tmon.platform.api.dto.TimeSlotDto;
import com.tmon.platform.api.dto.TimeSlotInformationDto;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.SQLCustomException;

/**
 * TimeSlotService
 * 
 * @author 구도원
 * 
 *         관리자만 접근 가능한 TimeSlot Service interface
 *
 */
public interface TimeSlotService {

	public Map<String, String> insert(String startTime, String endTime, String deliveryDate, int count)
			throws DateFormatException, SQLCustomException;

	public Map<String, String> update(String startTime, String endTime, int timeslotID)
			throws DateFormatException, SQLCustomException;

	public Map<String, String> delete(int timeslotID) throws SQLCustomException;

	public List<TimeSlotInformationDto> selectValid(String searchInitDate)
			throws DateFormatException, SQLCustomException;

	public List<TimeSlotInformationDto> selectBydeliveryDate(String searchInitDate, String searchFinishDate)
			throws DateFormatException, SQLCustomException;

}
