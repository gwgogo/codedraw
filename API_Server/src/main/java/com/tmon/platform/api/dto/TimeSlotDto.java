package com.tmon.platform.api.dto;

import java.sql.Time;
import java.util.Date;

/**
 * TimeSlotDto
 * 
 * @author 구도원
 *
 */
public class TimeSlotDto {

	private int timeslot_id;
	private Time start_time;
	private Time end_time;
	private Date delivery_date;
	private int count;
	private Date cutoff;

	public int getTimeslot_id() {
		return timeslot_id;
	}

	public void setTimeslot_id(int timeslot_id) {
		this.timeslot_id = timeslot_id;
	}

	public Time getStart_time() {
		return start_time;
	}

	public void setStart_time(Time date) {
		this.start_time = date;
	}

	public Time getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Time date) {
		this.end_time = date;
	}

	public Date getDelivery_date() {
		return delivery_date;
	}

	public void setDelivery_date(Date delivery_date) {
		this.delivery_date = delivery_date;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Date getCutoff() {
		return cutoff;
	}

	public void setCutoff(Date cutoff) {
		this.cutoff = cutoff;
	}

}
