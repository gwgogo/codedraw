package com.tmon.platform.api.dto;

import java.util.Date;
import java.util.List;

public class TimeSlotInformationDto {

	private Date delivery_date;
	private List<TimeSlotDto> timeList;

	public Date getDelivery_date() {
		return delivery_date;
	}

	public void setDelivery_date(Date delivery_date) {
		this.delivery_date = delivery_date;
	}

	public List<TimeSlotDto> getTimeList() {
		return timeList;
	}

	public void setTimeList(List<TimeSlotDto> timeList) {
		this.timeList = timeList;
	}

}
