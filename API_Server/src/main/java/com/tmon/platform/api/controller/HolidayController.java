package com.tmon.platform.api.controller;

import java.text.ParseException;
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

import com.tmon.platform.api.dto.HolidayDto;
import com.tmon.platform.api.exception.SQLCustomException;
import com.tmon.platform.api.service.HolidayService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * HolidayController
 * 
 * @author 구도원
 *
 */
@RestController
@RequestMapping(value = "/holidays")
@CrossOrigin
public class HolidayController {

	private static final Logger logger = LoggerFactory.getLogger(HolidayController.class);

	@Autowired
	HolidayService holidayService;

	/**
	 * 
	 * @param requestBody
	 * @return
	 * @throws SQLCustomException
	 */
	@ApiOperation(value = "공유일 입력", notes = "관리자가 공휴일을 신규로 HOLIDAY 테이블에 삽입")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "requestBody", value = "{ holidayLunar : value, holidayDate : value[yyyy-MM-dd], holidayTitle : value}", dataType = "String", paramType = "body")	
	})
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success Insert HOLIDAY SQL"),
			@ApiResponse(code = 617, message = "Fail Insert HOLIDAY SQL Error") })
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, String> insertHoliday(@RequestBody Map<String, String> requestBody) throws SQLCustomException {
		logger.info("This is insertHoliday");

		int holidayLunar = Integer.parseInt(requestBody.get("holidayLunar"));
		String holidayDate = requestBody.get("holidayDate");
		String holidayTitle = requestBody.get("holidayTitle");

		// INSERT Query 실행
		return holidayService.insert(holidayLunar, holidayDate, holidayTitle);
	}

	/**
	 * @author 구도원
	 * @param holidayID
	 * @param requestBody
	 * @return
	 * @throws SQLCustomException
	 */
	@ApiOperation(value = "공휴일 수정", notes = "관리자가 공휴일을 수정하는 API")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "holidayID", value = "수정할 공휴일의 고유번호", dataType = "Integer", paramType = "path"),
			@ApiImplicitParam(name = "requestBody", value = "{ holidayLunar : value, holidayDate : value[yyyy-MM-dd], holidayTitle : value}", dataType = "String", paramType = "body") })
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success Update HOLIDAY SQL"),
			@ApiResponse(code = 617, message = "Fail Update HOLIDAY SQL Error") })
	@RequestMapping(value = "/{holidayID}", method = RequestMethod.PUT)
	public Map<String, String> updateHoliday(
			@PathVariable("holidayID") int holidayID,
			@RequestBody Map<String, String> requestBody) throws SQLCustomException {
		logger.info("This is updateHoliday");

		int holidayLunar = Integer.parseInt(requestBody.get("holidayLunar"));
		String holidayDate = requestBody.get("holidayDate");
		String holidayTitle = requestBody.get("holidayTitle");

		// UPDATE Query 실행
		return holidayService.update(holidayLunar, holidayDate, holidayTitle, holidayID);

	}

	/**
	 * 
	 * @author 구도원
	 * @param holidayID
	 * @return
	 * @throws SQLCustomException
	 */
	@ApiOperation(value = "공휴일 삭제", notes = "관리자가 공휴일을 삭제하는 API")
	@ApiImplicitParams(value= {
		@ApiImplicitParam(name = "holidayID", value = "수정할 공휴일의 고유번호", dataType = "Integer", paramType = "path")	
	})
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success Delete HOLIDAY SQL"),
			@ApiResponse(code = 617, message = "Fail Delete HOLIDAY SQL Error") })
	@RequestMapping(value = "/{holidayID}", method = RequestMethod.DELETE)
	public Map<String, String> deleteHoliday(
			@PathVariable("holidayID") int holidayID) throws SQLCustomException {
		logger.info("This is deleteHoliday");

		// DELETE Query 실행
		return holidayService.delete(holidayID);
	}

	/**
	 * HOLIDAY TABLE로 부터의 데이터이다. holidayLunar는 양력, 음력을 구분하는 컬럼으로서 ENUM type이다.
	 * holidayLunar ENUM('양력', '음력')
	 * 
	 * 현재 세팅되어 있는 공휴일들의 목록이며 Batch Server는 물론 UI에서도 사용할 수 있다.
	 * 
	 * @author 구도원
	 * @return
	 * @throws SQLCustomException
	 */
	@ApiOperation(value = "세팅된 공휴일 목록", notes = "holidayLunar  1: 양력, 2:음력")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success Select HOLIDAY SQL"),
			@ApiResponse(code = 617, message = "Fail Select HOLIDAY SQL Error")
	})
	@RequestMapping(method = RequestMethod.GET)
	public List<HolidayDto> selectHoliday() throws SQLCustomException {
		logger.info("This is selectHoliday");

		return holidayService.select();
	}

	/**
	 * HOLIDAY TABLE로 부터의 데이터를 가공한다. 음력을 해당년도 양력 날짜로 변환한 데이터 이다.
	 * 
	 * @author 구도원
	 * @param year
	 * @return
	 * @throws ParseException
	 * @throws SQLCustomException
	 */
	@ApiOperation(value = "세팅된 공휴일 목록(입력된 년도)", notes = "holidayLunar  1: 양력, 2:음력")
	@ApiImplicitParams(value= {
			@ApiImplicitParam(name = "year", value = "확인할 년도", dataType = "Integer", paramType = "path") 
	})
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success Select HOLIDAY SQL"),
			@ApiResponse(code = 617, message = "Fail Select HOLIDAY SQL Error") 
	})
	@RequestMapping(value = "/{year}", method = RequestMethod.GET)
	public List<HolidayDto> selectHolidaythisYear(@PathVariable("year") int year)
			throws ParseException, SQLCustomException {
		logger.info("This is selectHolidaythisYear");

		// selectThisYear는 입력된 해당년도를 기준으로 음력날짜를 양력날짜로 변환하는 비즈니스 로직이다.
		return holidayService.selectBythisYear(year);
	}

}
