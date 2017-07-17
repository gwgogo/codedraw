package com.tmon.platform.api.dto;

import java.sql.Time;

/**
 * TimeSlotSettingDto
 * 
 * @author 구도원
 *
 */
public class TimeSlotSettingDto {

	private int timeslot_setting_id;
	private Time start_time;
	private Time end_time;

	public int getTimeslot_setting_id() {
		return timeslot_setting_id;
	}

	public void setTimeslot_setting_id(int timeslot_setting_id) {
		this.timeslot_setting_id = timeslot_setting_id;
	}

	public Time getStart_time() {
		return start_time;
	}

	public void setStart_time(Time start_time) {
		this.start_time = start_time;
	}

	public Time getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Time end_time) {
		this.end_time = end_time;
	}

}
