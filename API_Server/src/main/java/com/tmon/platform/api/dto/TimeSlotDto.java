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

	private int timeslotID;
	private Time startTime;
	private Time endTime;
	private Date deliveryDate;
	private int count;
	private Date cutoff;

	public int getTimeslotID() {
		return timeslotID;
	}

	public void setTimeslotID(int timeslotID) {
		this.timeslotID = timeslotID;
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

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
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
