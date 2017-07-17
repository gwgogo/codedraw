package com.tmon.platform.api.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

	@Autowired
	HolidayDao holidayDao;

	@Override
	public List<HolidayDto> select() {
		return holidayDao.select();
	}

	@Override
	public List<HolidayDto> selectThisYear(int year) {
		// DB로부터 공휴일 목록을 가져온다.
		List<HolidayDto> holidays = holidayDao.select();
		List<HolidayDto> holidays_Year = new ArrayList<HolidayDto>();
		LunarConverter lunarConverter = new LunarConverter();

		// 올해 날짜
		for (HolidayDto holidayDto : holidays) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(holidayDto.getHoliday_date());
			calendar.set(Calendar.YEAR, year);
			holidayDto.setHoliday_date(new Date(calendar.getTimeInMillis()));

			if (holidayDto.getHoliday_lunar() == 2) {
				// 음력날짜 반환
				Date lunarDate = holidayDto.getHoliday_date();

				// 양력날짜 반환(음력날짜가 변환된다)
				Date convertedDate = lunarConverter.convertToDate(lunarDate);

				// 변환된 양력날짜 입력
				holidayDto.setHoliday_date(convertedDate);
			}

			// 올해날짜로 최종 수정된것을 add한다.
			holidays_Year.add(holidayDto);
		}
		return holidays_Year;
	}

}
