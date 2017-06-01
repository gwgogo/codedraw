package kr.co.rentwheel.dto;

public class ReservationDto {
	private int rs_id;
	private String rs_user_id;
	private int rs_mobility_id;
	private String rs_start_time;
	private String rs_end_time;
	public int getRs_id() {
		return rs_id;
	}
	public void setRs_id(int rs_id) {
		this.rs_id = rs_id;
	}
	public String getRs_user_id() {
		return rs_user_id;
	}
	public void setRs_user_id(String rs_user_id) {
		this.rs_user_id = rs_user_id;
	}
	public int getRs_mobility_id() {
		return rs_mobility_id;
	}
	public void setRs_mobility_id(int rs_mobility_id) {
		this.rs_mobility_id = rs_mobility_id;
	}
	public String getRs_start_time() {
		return rs_start_time;
	}
	public void setRs_start_time(String rs_start_time) {
		this.rs_start_time = rs_start_time;
	}
	public String getRs_end_time() {
		return rs_end_time;
	}
	public void setRs_end_time(String rs_end_time) {
		this.rs_end_time = rs_end_time;
	}
	
}
