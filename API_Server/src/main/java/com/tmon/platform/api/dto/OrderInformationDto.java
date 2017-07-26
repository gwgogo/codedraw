package com.tmon.platform.api.dto;

import java.sql.Time;
import java.util.List;

/**
 * OrderInformationDto
 * 
 * @author 구도원
 * 
 *
 */
public class OrderInformationDto {

	private int reservation_id;
	private String reservation_date;
	private String cancel_date;
	private String user_id;
	private int status_id;
	private String status_string;
	private int timeslot_id;
	private String delivery_date; // {join TIMESLOT}
	private Time start_time; // {join TIMESLOT}
	private Time end_time; // {join TIMESLOT}
	private List<OrderProductDto> productList;

	public int getReservation_id() {
		return reservation_id;
	}

	public void setReservation_id(int reservation_id) {
		this.reservation_id = reservation_id;
	}

	public String getReservation_date() {
		return reservation_date;
	}

	public void setReservation_date(String reservation_date) {
		this.reservation_date = reservation_date;
	}

	public String getCancel_date() {
		return cancel_date;
	}

	public void setCancel_date(String cancel_date) {
		this.cancel_date = cancel_date;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}

	public String getStatus_string() {
		return status_string;
	}

	public void setStatus_string(String status_string) {
		this.status_string = status_string;
	}

	public int getTimeslot_id() {
		return timeslot_id;
	}

	public void setTimeslot_id(int timeslot_id) {
		this.timeslot_id = timeslot_id;
	}

	public String getDelivery_date() {
		return delivery_date;
	}

	public void setDelivery_date(String delivery_date) {
		this.delivery_date = delivery_date;
	}

	public Time getStart_time() {
		return start_time;
	}

	public void setStart_time(Time start_time) {
		this.start_time = start_time;
	}

	public Time getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Time end_time) {
		this.end_time = end_time;
	}

	public List<OrderProductDto> getProductList() {
		return productList;
	}

	public void setProductList(List<OrderProductDto> productList) {
		this.productList = productList;
	}

}
