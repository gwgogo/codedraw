package com.tmon.platform.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmon.platform.api.dto.HolidayDto;
import com.tmon.platform.api.service.HolidayService;

import io.swagger.annotations.ApiOperation;

/**
 * HolidayController
 * 
 * @author 구도원
 *
 */
@Controller
public class HolidayController {

	private static final Logger logger = LoggerFactory.getLogger(HolidayController.class);

	@Autowired
	HolidayService holidayService;

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
	@RequestMapping(value = "/select_holiday", method = RequestMethod.GET)
	@ResponseBody
	public List<HolidayDto> select_holiday() {
		logger.info("This is select_holiday");

		return holidayService.select();
	}

	/**
	 * HOLIDAY TABLE로 부터의 데이터를 가공한다. 음력을 해당년도 양력 날짜로 변환한 데이터 이다.
	 * 
	 * @param year
	 * @return List<HolidayDto> [JSON]
	 */

	@ApiOperation(value = "입력된 년도의 공휴일을 양력으로 전환한 목록")
	@RequestMapping(value = "/select_holidayThisYear", method = RequestMethod.GET)
	@ResponseBody
	public List<HolidayDto> select_holidayThisYear(@RequestParam("year") int year) {
		logger.info("This is select_holidayThisYear");

		// selectThisYear는 입력된 해당년도를 기준으로 음력날짜를 양력날짜로 변환하는 비즈니스 로직이다.
		return holidayService.selectThisYear(year);
	}

}
