package com.tmon.platform.api.dto;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * OrderInformationDto
 * 
 * @author 구도원
 * 
 *
 */
public class OrderInformationDto {

	private int reservationID;
	private Date reservationDate;
	private Date cancelDate;
	private String userID;
	private int statusID;
	private String statusString;
	private int timeslotID;
	private Date deliveryDate; // {join TIMESLOT}
	private Time startTime; // {join TIMESLOT}
	private Time endTime; // {join TIMESLOT}
	private List<OrderProductDto> productList;

	public int getReservationID() {
		return reservationID;
	}

	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}

	public Date getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public int getStatusID() {
		return statusID;
	}

	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public int getTimeslotID() {
		return timeslotID;
	}

	public void setTimeslotID(int timeslotID) {
		this.timeslotID = timeslotID;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
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

	public List<OrderProductDto> getProductList() {
		return productList;
	}

	public void setProductList(List<OrderProductDto> productList) {
		this.productList = productList;
	}

}
