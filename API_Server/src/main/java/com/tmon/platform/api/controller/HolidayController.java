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
import com.tmon.platform.api.service.HolidayService;

import io.swagger.annotations.ApiOperation;

/**
 * HolidayController
 * 
 * @author 구도원
 *
 */
@RestController
@RequestMapping(value = "/holiday")
@CrossOrigin
public class HolidayController {

	private static final Logger logger = LoggerFactory.getLogger(HolidayController.class);

	@Autowired
	HolidayService holidayService;

	/**
	 * @author 구도원
	 * @param holiday_lunar
	 * @param holiday_date
	 * @param holiday_title
	 */
	@ApiOperation(value = "공유일 입력(작업중)")
	@RequestMapping(method = RequestMethod.POST)
	public void insert_holiday(@RequestBody Map<String, String> requestParams) {
		logger.info("This is insert_holiday");

		int holiday_lunar = Integer.parseInt(requestParams.get("holiday_lunar"));
		String holiday_date = requestParams.get("holiday_date");
		String holiday_title = requestParams.get("holiday_title");

		// INSERT Query 실행
		holidayService.insert(holiday_lunar, holiday_date, holiday_title);
	}

	/**
	 * @author 구도원
	 * @param holiday_lunar
	 * @param holiday_date
	 * @param holiday_title
	 * @param holiday_id
	 */
	@ApiOperation(value = "공휴일 수정(작업중)")
	@RequestMapping(method = RequestMethod.PUT)
	public void update_holiday(@RequestBody Map<String, String> requestParams) {
		logger.info("This is update_holiday");

		int holiday_lunar = Integer.parseInt(requestParams.get("holiday_lunar"));
		String holiday_date = requestParams.get("holiday_date");
		String holiday_title = requestParams.get("holiday_title");
		int holiday_id = Integer.parseInt(requestParams.get("holiday_id"));

		// UPDATE Query 실행
		holidayService.update(holiday_lunar, holiday_date, holiday_title, holiday_id);
	}

	/**
	 * @author 구도원
	 * @param holiday_id
	 */
	@ApiOperation(value = "공휴일 삭제(작업중)")
	@RequestMapping(method = RequestMethod.DELETE)
	public void delete_holiday(@RequestBody Map<String, String> requestParams) {
		logger.info("This is delete_holiday");

		int holiday_id = Integer.parseInt(requestParams.get("holiday_id"));

		// DELETE Query 실행
		holidayService.delete(holiday_id);
	}

	/**
	 * HOLIDAY TABLE로 부터의 데이터이다. holiday_lunar는 양력, 음력을 구분하는 컬럼으로서 ENUM type이다.
	 * holiday_lunar ENUM('양력', '음력')
	 * 
	 * 현재 세팅되어 있는 공휴일들의 목록이며 Batch Server는 물론 UI에서도 사용할 수 있다.
	 * 
	 * @author 구도원
	 * @return List<HolidayDto> [JSON]
	 */
	@ApiOperation(value = "현재 세팅되어 있는 공휴일 목록\n" + "holiday_lunar ==> 1: 양력, 2: 음력")
	@RequestMapping(method = RequestMethod.GET)
	public List<HolidayDto> select_holiday() {
		logger.info("This is select_holiday");

		return holidayService.select();
	}

	/**
	 * HOLIDAY TABLE로 부터의 데이터를 가공한다. 음력을 해당년도 양력 날짜로 변환한 데이터 이다.
	 * 
	 * @param year
	 * @return List<HolidayDto> [JSON]
	 * @throws ParseException
	 */
	@ApiOperation(value = "입력된 년도의 공휴일을 양력으로 전환한 목록")
	@RequestMapping(value = "/{year}", method = RequestMethod.GET)
	public List<HolidayDto> select_holiday_thisYear(@PathVariable("year") int year) throws ParseException {
		logger.info("This is select_holiday_thisYear");

		// selectThisYear는 입력된 해당년도를 기준으로 음력날짜를 양력날짜로 변환하는 비즈니스 로직이다.
		return holidayService.selectBythisYear(year);
	}

}
