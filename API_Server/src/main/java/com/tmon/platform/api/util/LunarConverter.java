package com.tmon.platform.api.util;

import java.sql.Date;
import java.util.Calendar;

import com.ibm.icu.util.ChineseCalendar;

/**
 * LunarConverter
 * 
 * @author 구도원
 *
 */
public class LunarConverter {
	/**
	 * 음력날짜 ==> 양력날짜 변환
	 * 
	 * @author 구도원
	 * @param lunarDate[음력날짜]
	 * @return Date [양력 날짜]
	 */
	public Date convertToDate(Date lunarDate) {

		// lunarDate를 Calendar객체로 생성한다.
		// lunarDate를 사용하기 위해서는 Calendar객체를 이용한다.
		Calendar calendatelunar = Calendar.getInstance();
		calendatelunar.setTime(lunarDate);

		// 'chineseCalendar'를 입력된 음력날짜를 이용해 세팅한다.
		ChineseCalendar chineseCalendar = new ChineseCalendar();
		chineseCalendar.set(ChineseCalendar.EXTENDED_YEAR, calendatelunar.get(Calendar.YEAR) + 2637);
		chineseCalendar.set(ChineseCalendar.MONTH, calendatelunar.get(Calendar.MONTH) - 1);
		chineseCalendar.set(ChineseCalendar.DATE, calendatelunar.get(Calendar.DATE));

		// 음력날짜인 'chineseCalendar'를 양력날짜인 'calendar'로 변환한다.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(chineseCalendar.getTimeInMillis());

		// 다시 Date형태로 출력한다.
		return new Date(calendar.getTimeInMillis());
	}
}
