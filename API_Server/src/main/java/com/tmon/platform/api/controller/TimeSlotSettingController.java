package com.tmon.platform.api.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tmon.platform.api.dto.TimeSlotSettingDto;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.service.TimeSlotSettingService;

import io.swagger.annotations.ApiOperation;

/**
 * TimeSlotSettingController
 * 
 * @author 구도원
 *
 */
@RestController
@RequestMapping(value = "/timeslotsetting")
@CrossOrigin
public class TimeSlotSettingController {

	private static final Logger logger = LoggerFactory.getLogger(TimeSlotSettingController.class);

	@Autowired
	TimeSlotSettingService timeSlotSettingService;

	@ApiOperation(value = "타임슬롯의 시간대를 새롭게 추가하는 API")
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, String> insert_timeslotsetting(@RequestBody Map<String, String> requestParams)
			throws DateFormatException, SQLCustomException {
		logger.info("This is insert_timeslotsetting");

		String start_time = requestParams.get("start_time");
		String end_time = requestParams.get("end_time");

		// INSERT Query 실행
		return timeSlotSettingService.insert(start_time, end_time);
	}

	@ApiOperation(value = "기존의 타임슬롯 시간대를 조정(수정)하는 API")
	@RequestMapping(value = "/{timeslot_setting_id}", method = RequestMethod.PUT)
	public Map<String, String> update_timeslotsetting(@PathVariable("timeslot_setting_id") int timeslot_setting_id,
			@RequestBody Map<String, String> requestParams) throws DateFormatException, SQLCustomException {
		logger.info("This is update_timeslotsetting");

		String start_time = requestParams.get("start_time");
		String end_time = requestParams.get("end_time");

		// UPDATE Query 실행
		return timeSlotSettingService.update(timeslot_setting_id, start_time, end_time);
	}

	@ApiOperation(value = "기존의 타임슬롯 시간대를 삭제하는 API")
	@RequestMapping(value = "/{timeslot_setting_id}", method = RequestMethod.DELETE)
	public Map<String, String> delete_timeslotsetting(@PathVariable("timeslot_setting_id") int timeslot_setting_id)
			throws DateFormatException, SQLCustomException {
		logger.info("This is delete_timeslotsetting");

		// DELETE Query 실행
		return timeSlotSettingService.delete(timeslot_setting_id);
	}

	/**
	 * TIMESLOT_SETTING TABLE로 부터의 데이터이다.
	 * 
	 * 현재 세팅되어 있는 timeslot시간대의 모습이며, Batch Server 는 이 TIMESLOT_SETTING TABLE을 바탕으로
	 * TIMESLOT TABLE에 새로운 날짜의 timeslot을 INSERT한다.
	 * 
	 * @author 구도원
	 * @return
	 * @throws SQLCustomException
	 */
	@ApiOperation(value = "현재 세팅되어 있는 TimeSlot 시간대")
	@RequestMapping(method = RequestMethod.GET)
	public List<TimeSlotSettingDto> select_timeslotsetting() throws SQLCustomException {
		logger.info("This is select_timeslotsetting");

		return timeSlotSettingService.select();
	}
}
