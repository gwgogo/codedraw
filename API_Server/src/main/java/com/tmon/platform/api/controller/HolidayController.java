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
			@ApiImplicitParam(name = "requestBody", value = "{ holiday_lunar : value, holiday_date : value[yyyy-MM-dd], holiday_title : value}", dataType = "String", paramType = "body")	
	})
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success Insert HOLIDAY SQL"),
			@ApiResponse(code = 617, message = "Fail Insert HOLIDAY SQL Error") })
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, String> insert_holiday(@RequestBody Map<String, String> requestBody) throws SQLCustomException {
		logger.info("This is insert_holiday");

		int holiday_lunar = Integer.parseInt(requestBody.get("holiday_lunar"));
		String holiday_date = requestBody.get("holiday_date");
		String holiday_title = requestBody.get("holiday_title");

		// INSERT Query 실행
		return holidayService.insert(holiday_lunar, holiday_date, holiday_title);
	}

	/**
	 * @author 구도원
	 * @param holiday_id
	 * @param requestBody
	 * @return
	 * @throws SQLCustomException
	 */
	@ApiOperation(value = "공휴일 수정", notes = "관리자가 공휴일을 수정하는 API")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "holiday_id", value = "수정할 공휴일의 고유번호", dataType = "Integer", paramType = "path"),
			@ApiImplicitParam(name = "requestBody", value = "{ holiday_lunar : value, holiday_date : value[yyyy-MM-dd], holiday_title : value}", dataType = "String", paramType = "body") })
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success Update HOLIDAY SQL"),
			@ApiResponse(code = 617, message = "Fail Update HOLIDAY SQL Error") })
	@RequestMapping(value = "/{holiday_id}", method = RequestMethod.PUT)
	public Map<String, String> update_holiday(
			@PathVariable("holiday_id") int holiday_id,
			@RequestBody Map<String, String> requestBody) throws SQLCustomException {
		logger.info("This is update_holiday");

		int holiday_lunar = Integer.parseInt(requestBody.get("holiday_lunar"));
		String holiday_date = requestBody.get("holiday_date");
		String holiday_title = requestBody.get("holiday_title");

		// UPDATE Query 실행
		return holidayService.update(holiday_lunar, holiday_date, holiday_title, holiday_id);

	}

	/**
	 * 
	 * @author 구도원
	 * @param holiday_id
	 * @return
	 * @throws SQLCustomException
	 */
	@ApiOperation(value = "공휴일 삭제", notes = "관리자가 공휴일을 삭제하는 API")
	@ApiImplicitParams(value= {
		@ApiImplicitParam(name = "holiday_id", value = "수정할 공휴일의 고유번호", dataType = "Integer", paramType = "path")	
	})
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success Delete HOLIDAY SQL"),
			@ApiResponse(code = 617, message = "Fail Delete HOLIDAY SQL Error") })
	@RequestMapping(value = "/{holiday_id}", method = RequestMethod.DELETE)
	public Map<String, String> delete_holiday(
			@PathVariable("holiday_id") int holiday_id) throws SQLCustomException {
		logger.info("This is delete_holiday");

		// DELETE Query 실행
		return holidayService.delete(holiday_id);
	}

	/**
	 * HOLIDAY TABLE로 부터의 데이터이다. holiday_lunar는 양력, 음력을 구분하는 컬럼으로서 ENUM type이다.
	 * holiday_lunar ENUM('양력', '음력')
	 * 
	 * 현재 세팅되어 있는 공휴일들의 목록이며 Batch Server는 물론 UI에서도 사용할 수 있다.
	 * 
	 * @author 구도원
	 * @return
	 * @throws SQLCustomException
	 */
	@ApiOperation(value = "세팅된 공휴일 목록", notes = "holiday_lunar  1: 양력, 2:음력")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success Select HOLIDAY SQL"),
			@ApiResponse(code = 617, message = "Fail Select HOLIDAY SQL Error")
	})
	@RequestMapping(method = RequestMethod.GET)
	public List<HolidayDto> select_holiday() throws SQLCustomException {
		logger.info("This is select_holiday");

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
	@ApiOperation(value = "세팅된 공휴일 목록(입력된 년도)", notes = "holiday_lunar  1: 양력, 2:음력")
	@ApiImplicitParams(value= {
			@ApiImplicitParam(name = "year", value = "확인할 년도", dataType = "Integer", paramType = "path") 
	})
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success Select HOLIDAY SQL"),
			@ApiResponse(code = 617, message = "Fail Select HOLIDAY SQL Error") 
	})
	@RequestMapping(value = "/{year}", method = RequestMethod.GET)
	public List<HolidayDto> select_holiday_thisYear(@PathVariable("year") int year)
			throws ParseException, SQLCustomException {
		logger.info("This is select_holiday_thisYear");

		// selectThisYear는 입력된 해당년도를 기준으로 음력날짜를 양력날짜로 변환하는 비즈니스 로직이다.
		return holidayService.selectBythisYear(year);
	}

}
