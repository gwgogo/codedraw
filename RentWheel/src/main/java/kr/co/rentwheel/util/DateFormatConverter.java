package kr.co.rentwheel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatConverter {

	public Date stringToDate(String stringDate) {
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date= new Date();
		try {
			date = sdf.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public String dateToString(Date date) {

		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String stringDate = transFormat.format(date);
		return stringDate;

	}
}
