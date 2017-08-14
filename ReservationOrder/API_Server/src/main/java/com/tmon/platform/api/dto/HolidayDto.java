package com.tmon.platform.api.dto;

import java.util.Date;

import com.tmon.platform.api.exception.DateFormatException;
import com.tmon.platform.api.util.UtilForDate;

/**
 * HolidayDto
 * 
 * @author 구도원
 *
 */
public class HolidayDto {

	private int holidayID;
	private Date holidayDate;
	private String holidayTitle;

	public HolidayDto() {

	}

	public HolidayDto(int holidayID) {
		this.holidayID = holidayID;
	}

	public HolidayDto(String holidayDate, String holidayTitle) throws DateFormatException {
		/*
		 * holidayDate가 Format에 맞게 제대로 입력되었는지 확인한다. [String Type converts To Date Type]
		 */
		this.holidayDate = UtilForDate.convertToDate(holidayDate, "yyyy-MM-dd");
		this.holidayTitle = holidayTitle;
	}

	public HolidayDto(String holidayDate, String holidayTitle, int holidayID) throws DateFormatException {
		/*
		 * holidayDate가 Format에 맞게 제대로 입력되었는지 확인한다. [String Type converts To Date Type]
		 */
		this.holidayDate = UtilForDate.convertToDate(holidayDate, "yyyy-MM-dd");
		this.holidayTitle = holidayTitle;
		this.holidayID = holidayID;
	}

	public int getHolidayID() {
		return holidayID;
	}

	public void setHolidayID(int holidayID) {
		this.holidayID = holidayID;
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
