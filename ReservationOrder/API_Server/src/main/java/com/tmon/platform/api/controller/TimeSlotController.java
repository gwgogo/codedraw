package com.tmon.platform.api.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
 */
@RestController
@RequestMapping(value = "/timeslots")
@CrossOrigin
public class TimeSlotController {

	private static final Logger logger = LoggerFactory.getLogger(TimeSlotController.class);

	@Autowired
	TimeSlotService timeSlotService;

	@ApiOperation(value = "입력된 날짜로 부터 입력된 일 수만큼 타임슬롯 조회", notes = "입력일자로 부터 validDays만큼 일수를 출력합니다.(공휴일, 주말 제외)")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "searchInitDate", value = "검색 시작 날짜", dataType = "String", paramType = "query") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Select TIMESLOT SQL"),
			@ApiResponse(code = 616, message = "Incorrect input Date data"),
			@ApiResponse(code = 618, message = "Fail Select TimeSlot SQL Error, TimeSlot selectValid Error"),
			@ApiResponse(code = 623, message = "RequestParameterType Mismatch") })
	@RequestMapping(value = "/validDays", method = RequestMethod.GET)
	public List<TimeSlotInformationDto> selectTimeslotValidDate(@RequestParam("searchInitDate") String searchInitDate)
			throws DateFormatException, SQLCustomException, MethodArgumentTypeMismatchException {
		logger.info("This is selectTimeslotValidDate");

		// 타임슬롯 검색 '시작 날짜', '시작 날짜로 부터 유효한 날짜' 가 service parameter이다.
		return timeSlotService.selectValid(searchInitDate);
	}

	@ApiOperation(value = "입력된 날짜 기간의 타임슬롯 조회", notes = "입력된 기간(날짜)의 타임슬롯 목록출력 API입니다.")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "searchInitDate", value = "검색 시작 날짜", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "searchFinishDate", value = "검색 끝 날짜", dataType = "String", paramType = "query") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Select TIMESLOT SQL"),
			@ApiResponse(code = 616, message = "Incorrect input Date data"),
			@ApiResponse(code = 616, message = "searchInitDate must be smaller than searchFinishDate"),
			@ApiResponse(code = 618, message = "Fail Select TimeSlot SQL Error, TimeSlot selectBydeliveryDate Error"),
			@ApiResponse(code = 623, message = "RequestParameterType Mismatch") })
	@RequestMapping(method = RequestMethod.GET)
	public List<TimeSlotInformationDto> selectTimeslotDeliveryDate(
			@RequestParam("searchInitDate") String searchInitDate,
			@RequestParam("searchFinishDate") String searchFinishDate)
			throws DateFormatException, SQLCustomException, MethodArgumentTypeMismatchException {
		logger.info("This is selectTimeslotDeliveryDate");

		// 타임슬롯 검색 '시작 날짜', '끝 날짜'가 service parameter이다.
		return timeSlotService.selectBydeliveryDate(searchInitDate, searchFinishDate);
	}

}
