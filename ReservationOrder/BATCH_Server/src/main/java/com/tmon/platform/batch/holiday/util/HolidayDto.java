package com.tmon.platform.batch.holiday.util;

import java.sql.Date;

public class HolidayDto {
	private int holidayID;
	private int holidaySection;
	private Date holidayDate;
	private String holidayTitle;

	public int getHolidayID() {
		return holidayID;
	}

	public void setHolidayID(int holidayID) {
		this.holidayID = holidayID;
	}

	public int getHolidaySection() {
		return holidaySection;
	}

	public void setHolidaySection(int holidaySection) {
		this.holidaySection = holidaySection;
	}

	public Date getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayDate(Date holidayDate) {
		this.holidayDate = holidayDate;
	}

	public String getHolidayTitle() {
		return holidayTitle;
	}

	public void setHolidayTitle(String holidayTitle) {
		this.holidayTitle = holidayTitle;
	}

}
