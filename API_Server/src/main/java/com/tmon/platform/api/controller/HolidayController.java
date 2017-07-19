package com.tmon.platform.api.controller;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	 * @author 구도원
	 * @param holiday_lunar
	 * @param holiday_date
	 * @param holiday_title
	 */
	@ApiOperation(value = "공유일 입력(작업중)")
	@RequestMapping(value = "/insert_holiday", method = RequestMethod.POST)
	@CrossOrigin
	public void insert_holiday(@RequestParam("holiday_lunar") int holiday_lunar,
			@RequestParam("holiday_date") Date holiday_date, @RequestParam("holiday_title") String holiday_title) {
		logger.info("This is insert_holiday");

		HolidayDto holidayDto = new HolidayDto();

		// 새롭게 삽입되는 공휴일의 양력(1), 음력(2) 구분
		holidayDto.setHoliday_lunar(holiday_lunar);

		// 새롭게 삽입되는 공휴일의 날짜
		holidayDto.setHoliday_date(holiday_date);

		// 새롭게 삽입되는 공휴일 이름
		holidayDto.setHoliday_title(holiday_title);

		// INSERT Query 실행
		holidayService.insert(holidayDto);
	}

	/**
	 * @author 구도원
	 * @param holiday_lunar
	 * @param holiday_date
	 * @param holiday_title
	 * @param holiday_id
	 */
	@ApiOperation(value = "공휴일 수정(작업중)")
	@RequestMapping(value = "/update_holiday", method = RequestMethod.POST)
	@CrossOrigin
	public void update_holiday(@RequestParam("holiday_lunar") int holiday_lunar,
			@RequestParam("holiday_date") Date holiday_date, @RequestParam("holiday_title") String holiday_title,
			@RequestParam("holiday_id") int holiday_id) {
		logger.info("This is update_holiday");

		HolidayDto holidayDto = new HolidayDto();

		// 수정할 공휴일의 수정된 날짜
		holidayDto.setHoliday_date(holiday_date);

		// 수정할 공휴일의 수정할 양력(1), 음력(2) 구분
		holidayDto.setHoliday_lunar(holiday_lunar);

		// 수정할 공휴일의 공휴일 이름
		holidayDto.setHoliday_title(holiday_title);

		// 수정한 공휴일의 공휴일 고유번호
		holidayDto.setHoliday_id(holiday_id);

		// UPDATE Query 실행
		holidayService.update(holidayDto);
	}

	/**
	 * @author 구도원
	 * @param holiday_id
	 */
	@ApiOperation(value = "공휴일 삭제(작업중)")
	@RequestMapping(value = "/delete_holiday", method = RequestMethod.POST)
	@CrossOrigin
	public void delete_holiday(@RequestParam("holiday_id") int holiday_id) {
		logger.info("This is delete_holiday");

		HolidayDto holidayDto = new HolidayDto();

		// 삭제할 공휴일의 고유번호
		holidayDto.setHoliday_id(holiday_id);

		// DELETE Query 실행
		holidayService.delete(holidayDto);
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
	@RequestMapping(value = "/select_holiday", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
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
	@RequestMapping(value = "/select_holiday_thisYear", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public List<HolidayDto> select_holiday_thisYear(@RequestParam("year") int year) {
		logger.info("This is select_holiday_thisYear");

		// selectThisYear는 입력된 해당년도를 기준으로 음력날짜를 양력날짜로 변환하는 비즈니스 로직이다.
		return holidayService.selectBythisYear(year);
	}

}
