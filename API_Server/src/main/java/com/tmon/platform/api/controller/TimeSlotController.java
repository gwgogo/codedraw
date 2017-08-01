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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tmon.platform.api.dto.TimeSlotDto;
import com.tmon.platform.api.dto.TimeSlotInformationDto;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.service.TimeSlotService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * TimeSlotController
 * 
 * @author 구도원
 *
 */
@RestController
@RequestMapping(value = "/timeslots")
@CrossOrigin
public class TimeSlotController {

	private static final Logger logger = LoggerFactory.getLogger(TimeSlotController.class);

	@Autowired
	TimeSlotService timeSlotService;

	/**
	 * @author 구도원
	 * @param requestBody
	 * @return
	 * @throws DateFormatException
	 * @throws SQLCustomException
	 */
	@ApiOperation(value = "타임슬롯 신규 입력", notes = "타임슬롯 생성하는 API입니다. 시간 양식 [HH:mm:ss], 날짜양식 [yyyy-MM-dd]")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "requestBody", value = "{ startTime : value, endTime : value, deliveryDate : value, count : value}", dataType = "String", paramType = "body") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Insert TIMESLOT SQL"),
			@ApiResponse(code = 616, message = "Incorrect input Time or Date data"),
			@ApiResponse(code = 616, message = "startTime must be smaller than endTime"),
			@ApiResponse(code = 618, message = "Fail Insert TIMESLOT SQL Error") })
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, String> insertTimeslot(@RequestBody Map<String, String> requestBody)
			throws DateFormatException, SQLCustomException {
		logger.info("This is insertTimeslot");

		String startTime = requestBody.get("startTime");
		String endTime = requestBody.get("endTime");
		String deliveryDate = requestBody.get("deliveryDate");
		int count = Integer.parseInt(requestBody.get("count"));

		// INSERT Query 실행
		return timeSlotService.insert(startTime, endTime, deliveryDate, count);
	}

	/**
	 * @author 구도원
	 * @param timeslotID
	 * @param requestBody
	 * @return
	 * @throws DateFormatException
	 * @throws SQLCustomException
	 */
	@ApiOperation(value = "타임슬롯 수정", notes = "타임슬롯 시간대 변경하는 API입니다. 시간 양식 [HH:mm:ss], 날짜양식 [yyyy-MM-dd]")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "timeslotID", value = "타임슬롯 고유 번호", dataType = "Integer", paramType = "path"),
			@ApiImplicitParam(name = "requestBody", value = "{ startTime : value, endTime : value, deliveryDate : value}", dataType = "String", paramType = "body") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Update TIMESLOT SQL"),
			@ApiResponse(code = 616, message = "Incorrect input Time or Date data"),
			@ApiResponse(code = 616, message = "startTime must be smaller than endTime"),
			@ApiResponse(code = 618, message = "Fail Update TIMESLOT SQL Error") })
	@RequestMapping(value = "/{timeslotID}", method = RequestMethod.PUT)
	public Map<String, String> updateTimeslot(@PathVariable("timeslotID") int timeslotID,
			@RequestBody Map<String, String> requestBody) throws DateFormatException, SQLCustomException {
		logger.info("This is updateTimeslot");

		String startTime = requestBody.get("startTime");
		String endTime = requestBody.get("endTime");

		// UPDATE Query 실행
		return timeSlotService.update(startTime, endTime, timeslotID);
	}

	/**
	 * @author 구도원
	 * @param timeslotID
	 * @return
	 * @throws DateFormatException
	 * @throws SQLCustomException
	 */
	@ApiOperation(value = "타임슬롯 삭제", notes = "타임슬롯 삭제하는 API입니다.")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "timeslotID", value = "타임슬롯 고유 번호", dataType = "Integer", paramType = "path") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Delete TIMESLOT SQL"),
			@ApiResponse(code = 618, message = "Fail Delete TIMESLOT SQL Error") })
	@RequestMapping(value = "/{timeslotID}", method = RequestMethod.DELETE)
	public Map<String, String> deleteTimeslot(@PathVariable("timeslotID") int timeslotID) throws SQLCustomException {
		logger.info("This is deleteTimeslot");

		// DELETE Query 실행
		// 단 주문이 있는(즉, count>0인 경우) 타임슬롯은 삭제하지 않는다.
		return timeSlotService.delete(timeslotID);
	}

	/**
	 * @author 구도원
	 * @param searchInitDate
	 * @param validDays
	 * @return
	 * @throws DateFormatException
	 * @throws SQLCustomException
	 */
	@ApiOperation(value = "입력된 날짜로 부터 입력된 일 수만큼 타임슬롯 조회", notes = "입력일자로 부터 validDays만큼 일수를 출력합니다.(공휴일, 주말 제외)")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "searchInitDate", value = "검색 시작 날짜", dataType = "String", paramType = "query") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Select TIMESLOT SQL"),
			@ApiResponse(code = 616, message = "Incorrect input Date data"),
			@ApiResponse(code = 618, message = "Fail Select TimeSlot SQL Error, TimeSlot selectValid Error") })
	@RequestMapping(value = "/validDays", method = RequestMethod.GET)
	public List<TimeSlotInformationDto> selectTimeslotValidDate(@RequestParam("searchInitDate") String searchInitDate)
			throws DateFormatException, SQLCustomException {
		logger.info("This is selectTimeslotValidDate");

		// 타임슬롯 검색 '시작 날짜', '시작 날짜로 부터 유효한 날짜' 가 service parameter이다.
		return timeSlotService.selectValid(searchInitDate);
	}

	/**
	 * @author 구도원
	 * @param searchInitDate
	 * @param searchFinishDate
	 * @return
	 * @throws DateFormatException
	 * @throws SQLCustomException
	 */
	@ApiOperation(value = "입력된 날짜 기간의 타임슬롯 조회", notes = "입력된 기간(날짜)의 타임슬롯 목록출력 API입니다.")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "searchInitDate", value = "검색 시작 날짜", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "searchFinishDate", value = "검색 끝 날짜", dataType = "String", paramType = "query") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Select TIMESLOT SQL"),
			@ApiResponse(code = 616, message = "Incorrect input Date data"),
			@ApiResponse(code = 616, message = "searchInitDate must be smaller than searchFinishDate"),
			@ApiResponse(code = 618, message = "Fail Select TimeSlot SQL Error, TimeSlot selectBydeliveryDate Error") })
	@RequestMapping(method = RequestMethod.GET)
	public List<TimeSlotInformationDto> selectTimeslotDeliveryDate(
			@RequestParam("searchInitDate") String searchInitDate,
			@RequestParam("searchFinishDate") String searchFinishDate) throws DateFormatException, SQLCustomException {
		logger.info("This is selectTimeslotDeliveryDate");

		// 타임슬롯 검색 '시작 날짜', '끝 날짜'가 service parameter이다.
		return timeSlotService.selectBydeliveryDate(searchInitDate, searchFinishDate);
	}

}
