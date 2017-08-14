package com.tmon.platform.batch.snapshot.launch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.tmon.platform.batch.ServerSettingUtils;

public class SnapShotTime {

	/**
	 * 입력되는 long타입의 currTime을 지연된 서버시간을 보정하고(DELAY_SERVER_TIME_HOUR) 원하는 포맷으로 String
	 * 형태로 출력한다.
	 * 
	 * SECOND는 사용하지 않으므로 0을 선언하여 버림한다.
	 * 
	 * @param currTime
	 * @return snapshotTime
	 */
	public static String getSnapshotTime(long currTime) {

		Date currentDate = new Date(currTime);
		Calendar timeEditor = new GregorianCalendar();

		timeEditor.setTime(currentDate);
		timeEditor.add(Calendar.HOUR, ServerSettingUtils.DELAY_SERVER_TIME_HOUR);
		timeEditor.add(Calendar.MINUTE, 0);
		timeEditor.set(Calendar.SECOND, 0);
		timeEditor.set(Calendar.MILLISECOND, 0);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String snapshotTime = dateFormat.format(timeEditor.getTime());

		return snapshotTime;
	}

	/**
	 * 입력되는 long타입의 currTime을 지연된 서버시간을 보정하고(DELAY_SERVER_TIME_HOUR) 원하는 포맷으로 String
	 * 형태로 출력한다.
	 * 
	 * SnapShot의 대상이 되는 시작 시간이다.
	 * 
	 * SECOND는 사용하지 않으므로 0을 선언하여 버림한다. MINUTE는 10분간격이므로 -10을 한다.
	 * 
	 * @param currTime
	 * @return snapshotStarTime
	 */
	public static String getSnapshotStartTime(long currTime) {

		Date currentDate = new Date(currTime);
		Calendar timeEditor = Calendar.getInstance();

		timeEditor.setTime(currentDate);
		timeEditor.add(Calendar.HOUR, ServerSettingUtils.DELAY_SERVER_TIME_HOUR);
		timeEditor.add(Calendar.MINUTE, -10);
		timeEditor.set(Calendar.SECOND, 0);
		timeEditor.set(Calendar.MILLISECOND, 0);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String snapshotStartTime = dateFormat.format(timeEditor.getTime());

		return snapshotStartTime;
	}

	/**
	 * 입력되는 long타입의 currTime을 지연된 서버시간을 보정하고(DELAY_SERVER_TIME_HOUR) 원하는 포맷으로 String
	 * 형태로 출력한다.
	 * 
	 * SnapShot의 대상이 되는 끝 시간이다.
	 * 
	 * SECOND는 사용하지 않으므로 0을 선언하여 버림한다.
	 * 
	 * @param currTime
	 * @return snapshotEndTime
	 */
	public static String getSnapshotEndTime(long currTime) {

		Date currentDate = new Date(currTime);
		Calendar timeEditor = Calendar.getInstance();

		timeEditor.setTime(currentDate);
		timeEditor.add(Calendar.HOUR, ServerSettingUtils.DELAY_SERVER_TIME_HOUR);
		timeEditor.add(Calendar.MINUTE, 0);
		timeEditor.set(Calendar.SECOND, 0);
		timeEditor.set(Calendar.MILLISECOND, 0);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String snapshotEndTime = dateFormat.format(timeEditor.getTime());

		return snapshotEndTime;
	}

}
