package com.tmon.platform.api.dto;

public class ReservationDto {
	private int reservation_id;
	private String reservation_date;
	private String cancle_date;
	private String user_id;
	private int status_id;
	private int timeslot_id;
	
	
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
	
	public String getCancle_date() {
		return cancle_date;
	}
	public void setCancle_date(String cancle_date) {
		this.cancle_date = cancle_date;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String uer_id) {
		this.user_id = uer_id;
	}
	public int getStatus_id() {
		return status_id;
	}
	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}
	
	public int getTimeslot_id() {
		return timeslot_id;
	}
	public void setTimeslot_id(int timeslot_id) {
		this.timeslot_id = timeslot_id;
	}
}
