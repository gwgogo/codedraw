package com.tmon.platform.api.dto;

import java.sql.Time;

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
