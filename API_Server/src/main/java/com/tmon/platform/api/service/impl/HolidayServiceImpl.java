package com.tmon.platform.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.icu.util.Calendar;
import com.tmon.platform.api.dao.HolidayDao;
import com.tmon.platform.api.dto.HolidayDto;
import com.tmon.platform.api.service.HolidayService;
import com.tmon.platform.api.util.LunarConverter;

/**
 * HolidayServiceImpl
 * 
 * @author 구도원
 *
 */
@Service
public class HolidayServiceImpl implements HolidayService {

	private final static Logger logger = LoggerFactory.getLogger(HolidayServiceImpl.class);

	@Autowired
	HolidayDao holidayDao;

	@Autowired
	SimpleDateFormat dateFormat;

	@Override
	public int insert(int holiday_lunar, String holiday_date, String holiday_title) {

		HolidayDto holidayDto = new HolidayDto();

		// 새롭게 삽입되는 공휴일의 양력(1), 음력(2) 구분
		holidayDto.setHoliday_lunar(holiday_lunar);

		// 새롭게 삽입되는 공휴일의 날짜
		holidayDto.setHoliday_date(holiday_date);

		// 새롭게 삽입되는 공휴일 이름
		holidayDto.setHoliday_title(holiday_title);

		return holidayDao.insert(holidayDto);
	}

	@Override
	public int update(int holiday_lunar, String holiday_date, String holiday_title, int holiday_id) {

		HolidayDto holidayDto = new HolidayDto();

		// 수정할 공휴일의 수정된 날짜
		holidayDto.setHoliday_date(holiday_date);

		// 수정할 공휴일의 수정할 양력(1), 음력(2) 구분
		holidayDto.setHoliday_lunar(holiday_lunar);

		// 수정할 공휴일의 공휴일 이름
		holidayDto.setHoliday_title(holiday_title);

		// 수정한 공휴일의 공휴일 고유번호
		holidayDto.setHoliday_id(holiday_id);

		return holidayDao.update(holidayDto);
	}

	@Override
	public int delete(int holiday_id) {

		HolidayDto holidayDto = new HolidayDto();

		// 삭제할 공휴일의 고유번호
		holidayDto.setHoliday_id(holiday_id);

		return holidayDao.delete(holidayDto);
	}

	@Override
	public List<HolidayDto> select() {
		return holidayDao.select();
	}

	@Override
	public List<HolidayDto> selectBythisYear(int year) throws ParseException {
		// DB로부터 공휴일 목록을 가져온다.
		List<HolidayDto> holidays = holidayDao.select();

		// 입력된 'year' 해당년도의 양력에 맞게 변환된 공휴일 날짜를 저장하는 List 객체를 선언한다.
		List<HolidayDto> holidays_Year = new ArrayList<HolidayDto>();

		// 음력날짜를 양력날짜로 변환하기 위한 LunarConverter 객체를 생성한다.
		LunarConverter lunarConverter = new LunarConverter();

		// 올해에 맞게 양력날짜로 변환한다.
		for (HolidayDto holidayDto : holidays) {

			/**
			 * String으로 되어있는 날짜를 'java.util.Date'형식으로 바꿔준다.
			 * 
			 * 입력된 해당년도를 9999년에서 'year'로 바꿔준다.
			 * 
			 */
			Date holidayDate = new Date(dateFormat.parse(holidayDto.getHoliday_date()).getTime());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(holidayDate);
			calendar.set(Calendar.YEAR, year);
			holidayDto.setHoliday_date(dateFormat.format(calendar.getTime()));

			// HOLIDAY 테이블에 음력날짜만 양력으로 바꿔준다.
			if (holidayDto.getHoliday_lunar() == 2) {
				// 음력날짜
				Date lunarDate = new Date(dateFormat.parse(holidayDto.getHoliday_date()).getTime());

				Date convertedDate = lunarConverter.convertToDate(lunarDate);
				// 변환된 양력날짜 입력
				holidayDto.setHoliday_date(dateFormat.format(convertedDate));
			}

			// 올해날짜로 최종 수정된것을 add한다.
			holidays_Year.add(holidayDto);
		}
		return holidays_Year;
	}
}