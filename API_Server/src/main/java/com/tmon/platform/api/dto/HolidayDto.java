package com.tmon.platform.api.dto;

import java.sql.Date;

public class HolidayDto {
	private int holiday_id;
	private int holiday_lunar;
	private Date holiday_date;
	private String holiday_title;

	public int getHoliday_id() {
		return holiday_id;
	}

	public void setHoliday_id(int holiday_id) {
		this.holiday_id = holiday_id;
	}

	public int getHoliday_lunar() {
		return holiday_lunar;
	}

	public void setHoliday_lunar(int holiday_lunar) {
		this.holiday_lunar = holiday_lunar;
	}

	public Date getHoliday_date() {
		return holiday_date;
	}

	public void setHoliday_date(Date holiday_date) {
		this.holiday_date = holiday_date;
	}

	public String getHoliday_title() {
		return holiday_title;
	}

	public void setHoliday_title(String holiday_title) {
		this.holiday_title = holiday_title;
	}

}
