package com.tmon.platform.api.util;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tmon.platform.api.exception.DateFormatException;

/**
 * ConverToDate
 * 
 * @author 구도원
 *
 */
public class UtilForDate {

	public static Date convertToDate(String stringTypeDate, String dateFormat) throws DateFormatException {

		SimpleDateFormat simpleDateformat = new SimpleDateFormat(dateFormat);
		Date convertedDate = null;
		try {
			convertedDate = simpleDateformat.parse(stringTypeDate);
		} catch (ParseException e) {
			throw new DateFormatException(616, "Incorrect input Date data");
		} catch (Exception e) {
			throw new DateFormatException(616, "Converte To Date Error");
		}
		return convertedDate;
	}

	public static Time convertToTime(String stringTypeTime, String timeFormat) throws DateFormatException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat);
		Date convertedDate = null;
		try {
			convertedDate = simpleDateFormat.parse(stringTypeTime);
		} catch (ParseException e) {
			throw new DateFormatException(616, "Incorrect input Time data");
		} catch (Exception e) {
			throw new DateFormatException(616, "Converte To Date Error");
		}

		Time convertedTime = new Time(convertedDate.getTime());
		return convertedTime;
	}

	public static int compareDate(long date1, long date2) {
		if (date1 < date2) {
			return 1;
		} else if (date1 == date2) {
			return 0;
		} else {
			return -1;
		}
	}

}
