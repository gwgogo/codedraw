package com.tmon.platform.api.dto;

import java.util.Date;
import java.util.List;

public class TimeSlotInformationDto {

	private Date deliveryDate;
	private List<TimeSlotDto> timeList;

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public List<TimeSlotDto> getTimeList() {
		return timeList;
	}

	public void setTimeList(List<TimeSlotDto> timeList) {
		this.timeList = timeList;
	}

}
