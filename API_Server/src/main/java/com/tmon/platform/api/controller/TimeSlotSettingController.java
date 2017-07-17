package com.tmon.platform.api.controller;

import java.sql.Time;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmon.platform.api.dto.TimeSlotSettingDto;
import com.tmon.platform.api.service.TimeSlotSettingService;

import io.swagger.annotations.ApiOperation;

/**
 * TimeSlotSettingController
 * 
 * @author 구도원
 *
 */
@Controller
public class TimeSlotSettingController {

	private static final Logger logger = LoggerFactory.getLogger(TimeSlotSettingController.class);

	@Autowired
	TimeSlotSettingService timeSlotSettingService;

	/**
	 * TimeSlotSetting Insert 타임슬롯의 시간대를 새롭게 추가하는 API
	 * 
	 * @author 구도원
	 * @param start_time
	 * @param end_time
	 * @return redirect:select_timeslotsetting
	 */
	@ApiOperation(value = "타임슬롯의 시간대를 새롭게 추가하는 API")
	@RequestMapping(value = "insert_timeslotsetting", method = RequestMethod.GET)
	public String insert_timeslotsetting(@RequestParam("start_time") Time start_time,
			@RequestParam("end_time") Time end_time) {
		logger.info("This is insert_timeslotsetting");

		TimeSlotSettingDto timeSlotSettingDto = new TimeSlotSettingDto();

		// 새롭게 삽입되는 타임슬롯 시간대의 시작시간
		timeSlotSettingDto.setStart_time(start_time);

		// 새롭게 삽입되는 타임슬롯 시간대의 종료시간
		timeSlotSettingDto.setEnd_time(end_time);

		// INSERT Query 실행
		timeSlotSettingService.insert(timeSlotSettingDto);
		return "redirect:select_timeslotsetting";
	}

	/**
	 * TimeSlotSetting Update 타임슬롯의 시간대를 수정하는 API
	 * 
	 * @author 구도원
	 * @param timeslot_setting_id
	 * @param start_time
	 * @param end_time
	 * @return redirect:select_timeslotsetting
	 */
	@ApiOperation(value = "기존의 타임슬롯 시간대를 조정(수정)하는 API")
	@RequestMapping(value = "update_timeslotsetting", method = RequestMethod.GET)
	public String update_timeslotsetting(@RequestParam("timeslot_setting_id") int timeslot_setting_id,
			@RequestParam("start_time") Time start_time, @RequestParam("end_time") Time end_time) {
		logger.info("This is update_timeslotsetting");

		TimeSlotSettingDto timeSlotSettingDto = new TimeSlotSettingDto();

		// 수정할 타임슬롯 시간대의 고유번호
		timeSlotSettingDto.setTimeslot_setting_id(timeslot_setting_id);

		// 수정할 타임슬롯 시간대의 시작시간
		timeSlotSettingDto.setStart_time(start_time);

		// 수정할 타임슬롯 시간대의 종료시간
		timeSlotSettingDto.setEnd_time(end_time);

		// UPDATE Query 실행
		timeSlotSettingService.update(timeSlotSettingDto);
		return "redirect:select_timeslotsetting";
	}

	/**
	 * TimeSlotSetting Delete 타임슬롯의 시간대를 삭제하는 API
	 * 
	 * @author 구도원
	 * @param timeslot_setting_id
	 * @return
	 */

	@ApiOperation(value = "기존의 타임슬롯 시간대를 삭제하는 API")
	@RequestMapping(value = "delete_timeslotsetting", method = RequestMethod.GET)
	public String delete_timeslotsetting(@RequestParam("timeslot_setting_id") int timeslot_setting_id) {
		logger.info("This is delete_timeslotsetting");

		TimeSlotSettingDto timeSlotSettingDto = new TimeSlotSettingDto();

		// 삭제할 타임슬롯 시간대의 고유번호
		timeSlotSettingDto.setTimeslot_setting_id(timeslot_setting_id);

		// DELETE Query 실행
		timeSlotSettingService.delete(timeSlotSettingDto);
		return "redirect:select_timeslotsetting";
	}

	/**
	 * TIMESLOT_SETTING TABLE로 부터의 데이터이다.
	 * 
	 * 현재 세팅되어 있는 timeslot시간대의 모습이며, Batch Server 는 이 TIMESLOT_SETTING TABLE을 바탕으로
	 * TIMESLOT TABLE에 새로운 날짜의 timeslot을 INSERT한다.
	 * 
	 * @author 구도원
	 * @return List<TimeSlotSettingDto> [JSON]
	 */
	@ApiOperation(value = "현재 세팅되어 있는 TimeSlot 시간대")
	@RequestMapping(value = "/select_timeslotsetting", method = RequestMethod.GET)
	@ResponseBody
	public List<TimeSlotSettingDto> select_timeslotsetting() {
		logger.info("This is select_timeslotsetting");

		return timeSlotSettingService.select();
	}
}
