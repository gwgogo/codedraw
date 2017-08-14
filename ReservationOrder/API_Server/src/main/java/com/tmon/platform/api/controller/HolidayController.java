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

import com.tmon.platform.api.dto.HolidayDto;
import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.exception.NullCustomException;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.exception.StopServiceException;
import com.tmon.platform.api.service.HolidayService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * HolidayController
 * 
 * @author 구도원
 */
@RestController
@RequestMapping(value = "/holidays")
@CrossOrigin
public class HolidayController {

	private static final Logger logger = LoggerFactory.getLogger(HolidayController.class);

	@Autowired
	HolidayService holidayService;

	@ApiOperation(value = "공유일 입력", notes = "관리자가 공휴일을 신규로 HOLIDAY 테이블에 삽입")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "requestBody", value = "{ \"holidayDate\" : value[yyyy-MM-dd], \"holidayTitle\" : value}", dataType = "String", paramType = "body") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Insert HOLIDAY SQL"),
			@ApiResponse(code = 617, message = "Fail Insert HOLIDAY SQL Error"),
			@ApiResponse(code = 616, message = "Incorrect input Date data"),
			@ApiResponse(code = 621, message = "holidaySection must be '2'"),
			@ApiResponse(code = 628, message = "Length of 'String' Type parameter must not be zero(0)"),
			@ApiResponse(code = 623, message = "RequestParameterType Mismatch") })
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, String> insertHoliday(@RequestBody Map<String, String> requestBody)
			throws SQLCustomException, DateFormatException, StopServiceException, MethodArgumentTypeMismatchException {
		logger.info("This is insertHoliday");

		String holidayDate = requestBody.get("holidayDate");
		String holidayTitle = requestBody.get("holidayTitle");

		// INSERT Query 실행
		return holidayService.insert(holidayDate, holidayTitle);
	}

	@ApiOperation(value = "공휴일 수정", notes = "관리자가 공휴일을 수정하는 API")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "holidayID", value = "수정할 공휴일의 고유번호", dataType = "int", paramType = "path"),
			@ApiImplicitParam(name = "requestBody", value = "{ \"holidayDate\" : value[yyyy-MM-dd], \"holidayTitle\" : value}", dataType = "String", paramType = "body") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Update HOLIDAY SQL"),
			@ApiResponse(code = 617, message = "Fail Update HOLIDAY SQL Error"),
			@ApiResponse(code = 616, message = "Incrrect input Date data"),
			@ApiResponse(code = 621, message = "holidaySection must be '2'"),
			@ApiResponse(code = 628, message = "Length of 'String' Type parameter must not be zero(0)"),
			@ApiResponse(code = 622, message = "There isn't HolidayDto data"),
			@ApiResponse(code = 623, message = "RequestParameterType Mismatch") })
	@RequestMapping(value = "/{holidayID}", method = RequestMethod.PUT)
	public Map<String, String> updateHoliday(@PathVariable("holidayID") int holidayID,
			@RequestBody Map<String, String> requestBody) throws SQLCustomException, DateFormatException,
			StopServiceException, NullCustomException, MethodArgumentTypeMismatchException {
		logger.info("This is updateHoliday");

		String holidayDate = requestBody.get("holidayDate");
		String holidayTitle = requestBody.get("holidayTitle");

		// UPDATE Query 실행
		return holidayService.update(holidayDate, holidayTitle, holidayID);
	}

	@ApiOperation(value = "공휴일 삭제", notes = "관리자가 공휴일을 삭제하는 API")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "holidayID", value = "수정할 공휴일의 고유번호", dataType = "int", paramType = "path") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Delete HOLIDAY SQL"),
			@ApiResponse(code = 617, message = "Fail Delete HOLIDAY SQL Error"),
			@ApiResponse(code = 621, message = "holidaySection must be '2'"),
			@ApiResponse(code = 628, message = "Length of 'String' Type parameter must not be zero(0)"),
			@ApiResponse(code = 622, message = "There isn't HolidayDto data"),
			@ApiResponse(code = 623, message = "RequestParameterType Mismatch") })
	@RequestMapping(value = "/{holidayID}", method = RequestMethod.DELETE)
	public Map<String, String> deleteHoliday(@PathVariable("holidayID") int holidayID)
			throws SQLCustomException, StopServiceException, NullCustomException, MethodArgumentTypeMismatchException {
		logger.info("This is deleteHoliday");

		// DELETE Query 실행
		return holidayService.delete(holidayID);
	}

	@ApiOperation(value = "세팅된 공휴일 목록(입력된 년도)", notes = "holidaySection  1: 법정, 2: 임시")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "year", value = "확인할 년도", dataType = "int", paramType = "path") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success Select HOLIDAY SQL"),
			@ApiResponse(code = 617, message = "Fail Select HOLIDAY SQL Error"),
			@ApiResponse(code = 623, message = "RequestParameterType Mismatch") })
	@RequestMapping(value = "/{year}", method = RequestMethod.GET)
	public List<HolidayDto> selectHolidaythisYear(@PathVariable("year") int year)
			throws SQLCustomException, MethodArgumentTypeMismatchException {
		logger.info("This is selectHolidaythisYear");

		// selectThisYear는 입력된 해당년도를 기준으로 음력날짜를 양력날짜로 변환하는 비즈니스 로직이다.
		return holidayService.selectBythisYear(year);
	}

}
