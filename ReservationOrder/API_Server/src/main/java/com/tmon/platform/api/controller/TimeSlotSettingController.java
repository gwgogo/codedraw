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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.tmon.platform.api.dto.TimeSlotSettingDto;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.service.TimeSlotSettingService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * TimeSlotSettingController
 * 
 * @author 구도원
 */
@RestController
@RequestMapping(value = "/timeslotsetting")
@CrossOrigin
public class TimeSlotSettingController {

	private static final Logger logger = LoggerFactory.getLogger(TimeSlotSettingController.class);

	@Autowired
	TimeSlotSettingService timeSlotSettingService;

	@ApiOperation(value = "타임슬롯 설정 신규 입력", notes = "타임슬롯 세팅 테이블에 시간대를 새롭게 추가할 수 있습니다.")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "requestBody", value = "{ \"startTime\" : value,\n\"endTime\" : value,\n\"cutoff\" : value }", dataType = "String", paramType = "body") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Insert TIMESLOT_SETTING SQL"),
			@ApiResponse(code = 616, message = "Incorrect input Time data"),
			@ApiResponse(code = 616, message = "startTime must be smaller than endTime"),
			@ApiResponse(code = 619, message = "Fail Insert TIMESLOT_SETTING SQL Error"),
			@ApiResponse(code = 623, message = "RequestParameterType Mismatch") })
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, String> insertTimeslotsetting(@RequestBody Map<String, String> requestBody)
			throws DateFormatException, SQLCustomException, MethodArgumentTypeMismatchException {
		logger.info("This is insertTimeslotsetting");

		String startTime = requestBody.get("startTime");
		String endTime = requestBody.get("endTime");
		String cutoff = requestBody.get("cutoff");

		// INSERT Query 실행
		return timeSlotSettingService.insert(startTime, endTime, cutoff);
	}

	@ApiOperation(value = "타임슬롯 설정 수정", notes = "타임슬롯 세팅 테이블에 시간대를 수정할 수 있습니다.")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "timeslotSettingID", value = "타임슬롯 세팅 고유번호", dataType = "int", paramType = "path"),
			@ApiImplicitParam(name = "requestBody", value = "{ \"startTime\" : value,\n \"endTime\" : value\n, \"cutoff\" : value }", dataType = "String", paramType = "body") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Update TIMESLOT_SETTING SQL"),
			@ApiResponse(code = 616, message = "Incorrect input Time data"),
			@ApiResponse(code = 616, message = "startTime must be smaller than endTime"),
			@ApiResponse(code = 619, message = "Fail Update TIMESLOT_SETTING SQL Error"),
			@ApiResponse(code = 624, message = "There isn't TimeSlotSettingDto data"),
			@ApiResponse(code = 623, message = "RequestParameterType Mismatch") })
	@RequestMapping(value = "/{timeslotSettingID}", method = RequestMethod.PUT)
	public Map<String, String> updateTimeslotsetting(@PathVariable("timeslotSettingID") int timeslotSettingID,
			@RequestBody Map<String, String> requestBody)
			throws DateFormatException, SQLCustomException, NullCustomException, MethodArgumentTypeMismatchException {
		logger.info("This is updateTimeslotsetting");

		String startTime = requestBody.get("startTime");
		String endTime = requestBody.get("endTime");
		String cutoff = requestBody.get("cutoff");

		// UPDATE Query 실행
		return timeSlotSettingService.update(timeslotSettingID, startTime, endTime, cutoff);
	}

	@ApiOperation(value = "타임슬롯 설정을 삭제", notes = "타임슬롯 세팅 테입릉에 시간대를 삭제할 수 있습니다.")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "timeslotSettingID", value = "타임슬롯 세팅 고유번호", dataType = "int", paramType = "path") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Delete TIMESLOT_SETTING SQL"),
			@ApiResponse(code = 619, message = "Fail Delete TIMESLOT_SETTING SQL Error"),
			@ApiResponse(code = 624, message = "There isn't TimeSlotSettingDto data"),
			@ApiResponse(code = 623, message = "RequestParameterType Mismatch") })
	@RequestMapping(value = "/{timeslotSettingID}", method = RequestMethod.DELETE)
	public Map<String, String> deleteTimeslotsetting(@PathVariable("timeslotSettingID") int timeslotSettingID)
			throws DateFormatException, SQLCustomException, NullCustomException, MethodArgumentTypeMismatchException {
		logger.info("This is deleteTimeslotsetting");

		// DELETE Query 실행
		return timeSlotSettingService.delete(timeslotSettingID);
	}

	/**
	 * TIMESLOT_SETTING TABLE로 부터의 데이터이다.
	 * 
	 * 현재 세팅되어 있는 timeslot시간대의 모습이며, Batch Server 는 이 TIMESLOT_SETTING TABLE을 바탕으로
	 * TIMESLOT TABLE에 새로운 날짜의 timeslot을 INSERT한다.
	 */
	@ApiOperation(value = "타임슬롯 설정을 전체 출력", notes = "현재 세팅되어 있는 TimeSlot 시간대")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Select TIMESLOT_SETTING SQL"),
			@ApiResponse(code = 619, message = "Fail Select TIMESLOT_SETTING SQL Error, TimeSlotSetting Select Error") })
	@RequestMapping(method = RequestMethod.GET)
	public List<TimeSlotSettingDto> selectTimeslotsetting() throws SQLCustomException {
		logger.info("This is selectTimeslotsetting");

		return timeSlotSettingService.select();
	}
}
