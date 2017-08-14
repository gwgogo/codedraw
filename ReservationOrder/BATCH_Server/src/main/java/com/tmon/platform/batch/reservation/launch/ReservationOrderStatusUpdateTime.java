package com.tmon.platform.batch.reservation.launch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.tmon.platform.batch.ServerSettingUtils;

public class ReservationOrderStatusUpdateTime {

	/**
	 * 입력되는 long타입의 currTime을 지연된 서버시간을 보정하고(DELAY_SERVER_TIME_HOUR) 원하는 포맷으로
	 * String형태로 출력한다.
	 * 
	 * @param currTime
	 * @return searchDeliveryDate
	 */
	public static String getSearchDeliveryDate(long currTime, String format) {
		Date searchInitDate = new Date(currTime);
		Calendar timeEditor = new GregorianCalendar();

		timeEditor.setTime(searchInitDate);
		timeEditor.add(Calendar.HOUR, ServerSettingUtils.DELAY_SERVER_TIME_HOUR);

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String searchDeliveryDate = dateFormat.format(timeEditor.getTime());

		return searchDeliveryDate;
	}

	/**
	 * 
	 * 입력되는 long타입의 currTime을 지연된 서버시간을 보정하고(DELAY_SERVER_TIME_HOUR) 원하는 포맷으로
	 * String형태로 출력한다.
	 * 
	 * @param currTime
	 * @param format
	 * @return searchFinishTime
	 */
	public static String getSearchFinishDate(long currTime, String format) {
		Date searchFinishDate = new Date(currTime);
		Calendar timeEditor = new GregorianCalendar();

		timeEditor.setTime(searchFinishDate);
		timeEditor.add(Calendar.HOUR, ServerSettingUtils.DELAY_SERVER_TIME_HOUR);
		timeEditor.set(Calendar.MILLISECOND, 0);
		timeEditor.set(Calendar.SECOND, 0);
		timeEditor.add(Calendar.MINUTE, 1);

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String searchFinishTime = dateFormat.format(timeEditor.getTime());

		return searchFinishTime;
	}
}
