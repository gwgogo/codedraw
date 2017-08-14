package com.tmon.platform.batch.holiday.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ConfirmHoliday
 * 
 * @author 구도원
 * 
 *         입력된 날짜가 DB에 지정된 공휴일인지를 확인하는 utility 클래스(메소드)
 *
 */
@Service
public class ConfirmHoliday {

	@Autowired
	SqlSession sqlSession;

	public boolean confirmHoliday(Calendar inputDayCalendar) {
		/*
		 * 입력되는 inputDayCalendar의 년도를 구한다.
		 */
		int year = inputDayCalendar.get(Calendar.YEAR);

		/*
		 * DB로부터 공휴일 목록을 가져온다. 해당년도에 속하는 공휴일을 모두를 가져온다.
		 */
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("year", String.valueOf(year));
		parameters.put("yearAfter1", String.valueOf(year + 1));
		List<HolidayDto> holidays = sqlSession.selectList("HolidayMapper.selectBythisYear", parameters);

		/*
		 * 입력된 날짜 (YYYY-MM-DD)가 공휴일 날짜목록(YYYY-MM-DD)과 일치하는 것이 있다면 confirmHoliday method는
		 * 'true'를 반환한다.
		 */
		for (HolidayDto holidayDto : holidays) {

			/* 날짜 비교를 위해 'yyyy-MM-dd' 포맷으로 변환한다. */
			String inputDayformat = new SimpleDateFormat("yyyy-MM-dd").format(inputDayCalendar.getTime());

			/* 날짜 비교를 위해 'yyyy-MM-dd' 포맷으로 변환한다. */
			String holidayformat = new SimpleDateFormat("yyyy-MM-dd").format(holidayDto.getHolidayDate().getTime());

			/* 입력된 날짜와 공휴일 목록중 하나의 날짜와 비교 */
			if (inputDayformat.equals(holidayformat)) {
				/* 입력된 날짜가 공휴일이면 true */
				return true;
			}
		}
		return false;
	}
}
