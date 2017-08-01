package com.tmon.platform.api.dto;

/**
 * HolidayDto
 * 
 * @author 구도원
 *
 */
public class HolidayDto {

	private int holidayID;
	private int holidayLunar;
	private String holidayDate;
	private String holidayTitle;

	public int getHolidayID() {
		return holidayID;
	}

	public void setHolidayID(int holidayID) {
		this.holidayID = holidayID;
	}

	public int getHolidayLunar() {
		return holidayLunar;
	}

	public void setHolidayLunar(int holidayLunar) {
		this.holidayLunar = holidayLunar;
	}

	public String getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayDate(String holidayDate) {
		this.holidayDate = holidayDate;
	}

	public String getHolidayTitle() {
		return holidayTitle;
	}

	public void setHolidayTitle(String holidayTitle) {
		this.holidayTitle = holidayTitle;
	}

}
