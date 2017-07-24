package com.tmon.platform.api.dto;

/**
 * TimeSlotSettingDto
 * 
 * @author 구도원
 *
 */
public class TimeSlotSettingDto {

	private int timeslot_setting_id;
	private String start_time;
	private String end_time;

	public int getTimeslot_setting_id() {
		return timeslot_setting_id;
	}

	public void setTimeslot_setting_id(int timeslot_setting_id) {
		this.timeslot_setting_id = timeslot_setting_id;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

}
