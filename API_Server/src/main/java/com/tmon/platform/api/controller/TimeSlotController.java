package com.tmon.platform.api.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmon.platform.api.dto.TimeSlotDto;
import com.tmon.platform.api.exception.TimeSlotException;
import com.tmon.platform.api.service.TimeSlotService;

import io.swagger.annotations.ApiOperation;

/**
 * TimeSlotController
 * 
 * @author 구도원
 * 
 *         본 Controller는 관리자만을 위한 Controller입니다. 별도의 Interceptor를 구성하여 관리자가
 *         아닌경우에는 본 Controller에 접근하지 못하도록 막을 계획입니다.
 *
 */
@Controller
public class TimeSlotController {

	private static final Logger logger = LoggerFactory.getLogger(TimeSlotController.class);

	@Autowired
	TimeSlotService timeSlotService;

	/**
	 * @author 구도원
	 * @param start_time
	 * @param end_time
	 * @param delivery_date
	 * @param count
	 * @param cutoff
	 * @throws TimeSlotException
	 */
	@ApiOperation(value = "타임슬롯 생성하는 API입니다. 시간 양식 [HH:mm:ss], 날짜양식 [yyyy-MM-dd]")
	@RequestMapping(value = "/admin_insert_timeslot", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Map<String, String> insert_timeslot(@RequestParam("start_time") String start_time,
			@RequestParam("end_time") String end_time, @RequestParam("delivery_date") String delivery_date,
			@RequestParam(value = "count", required = false, defaultValue = "0") int count) throws TimeSlotException {
		logger.info("This is insert_timeslot");

		// INSERT Query 실행
		return timeSlotService.insert(start_time, end_time, delivery_date, count);
	}

	/**
	 * @author 구도원
	 * @param start_time
	 * @param end_time
	 * @param timeslot_id
	 * @throws TimeSlotException
	 */
	@ApiOperation(value = "타임슬롯 시간대 변경하는 API입니다. 시간 양식 [HH:mm:ss], 날짜양식 [yyyy-MM-dd]")
	@RequestMapping(value = "/admin_update_timeslot", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Map<String, String> update_timeslot(@RequestParam("start_time") String start_time,
			@RequestParam("end_time") String end_time, @RequestParam("timeslot_id") int timeslot_id)
			throws TimeSlotException {
		logger.info("This is update_timeslot");

		// UPDATE Query 실행
		return timeSlotService.update(start_time, end_time, timeslot_id);
	}

	/**
	 * @author 구도원
	 * @param timeslot_id
	 * @throws TimeSlotException
	 */
	@ApiOperation(value = "타임슬롯 삭제하는 API입니다.")
	@RequestMapping(value = "/admin_delete_timeslot", method = RequestMethod.DELETE)
	@ResponseBody
	@CrossOrigin
	public Map<String, String> delete_timeslot(@RequestParam("timeslot_id") int timeslot_id) throws TimeSlotException {
		logger.info("This is delete_timeslot");

		// DELETE Query 실행
		// 단 주문이 있는(즉, count>0인 경우) 타임슬롯은 삭제하지 않는다.
		return timeSlotService.delete(timeslot_id);
	}

	/**
	 * @author 구도원
	 * @param init_date
	 * @param finish_date
	 * @return List<TimeSlotDto> [JSON]
	 * @throws TimeSlotException
	 */
	@ApiOperation(value = "입력된 기간(날짜)의 타임슬롯 목록출력 API입니다.")
	@RequestMapping(value = "/select_timeslot_delivery_date", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public List<TimeSlotDto> select_timeslot_delivery_date(@RequestParam("search_init_date") String search_init_date,
			@RequestParam("search_finish_date") String search_finish_date) throws TimeSlotException {
		logger.info("This is select_timeslot_delivery_date");

		// 타임슬롯 검색 '시작 날짜', '끝 날짜'가 service parameter이다.
		return timeSlotService.selectBydelivery_date(search_init_date, search_finish_date);
	}

}
