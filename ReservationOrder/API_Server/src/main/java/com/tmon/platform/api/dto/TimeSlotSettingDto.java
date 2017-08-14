package com.tmon.platform.api.dto;

import java.sql.Time;

import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.util.UtilForDate;

/**
 * TimeSlotSettingDto
 * 
 * @author 구도원
 *
 */
public class TimeSlotSettingDto {

	private int timeslotSettingID;
	private Time startTime;
	private Time endTime;
	private Time cutoff;

	public TimeSlotSettingDto() {

	}

	public TimeSlotSettingDto(int timeslotSettingID) {
		this.timeslotSettingID = timeslotSettingID;
	}

	public TimeSlotSettingDto(int timeslotSettingID, String startTime, String endTime, String cutoff)
			throws DateFormatException {
		this.timeslotSettingID = timeslotSettingID;
		/*
		 * statTime, endTime이 Format에 맞게 제대로 입력되었는지 확인한다. [String Type converts To Time
		 * Type]
		 */
		this.startTime = UtilForDate.convertToTime(startTime, "HH:mm:ss");
		this.endTime = UtilForDate.convertToTime(endTime, "HH:mm:ss");
		this.cutoff = UtilForDate.convertToTime(cutoff, "HH:mm:ss");
	}

	public TimeSlotSettingDto(String startTime, String endTime, String cutoff) throws DateFormatException {
		/*
		 * statTime, endTime이 Format에 맞게 제대로 입력되었는지 확인한다. [String Type converts To Time
		 * Type]
		 */
		this.startTime = UtilForDate.convertToTime(startTime, "HH:mm:ss");
		this.endTime = UtilForDate.convertToTime(endTime, "HH:mm:ss");
		this.cutoff = UtilForDate.convertToTime(cutoff, "HH:mm:ss");
	}

	public int getTimeslotSettingID() {
		return timeslotSettingID;
	}

	public void setTimeslotSettingID(int timeslotSettingID) {
		this.timeslotSettingID = timeslotSettingID;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public Time getCutoff() {
		return cutoff;
	}

	public void setCutoff(Time cutoff) {
		this.cutoff = cutoff;
	}
}
