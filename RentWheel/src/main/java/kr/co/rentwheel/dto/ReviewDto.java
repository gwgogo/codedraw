package kr.co.rentwheel.dto;

public class ReviewDto {
	private int rv_id;
	private String rv_msg;
	private int rv_score;
	private String rv_registDatetime;
	private String rv_user_name;
	private String rv_mobility_name;
	public int getRv_id() {
		return rv_id;
	}
	public void setRv_id(int rv_id) {
		this.rv_id = rv_id;
	}
	public String getRv_msg() {
		return rv_msg;
	}
	public void setRv_msg(String rv_msg) {
		this.rv_msg = rv_msg;
	}
	public int getRv_score() {
		return rv_score;
	}
	public void setRv_score(int rv_score) {
		this.rv_score = rv_score;
	}
	public String getRv_registDatetime() {
		return rv_registDatetime;
	}
	public void setRv_registDatetime(String rv_registDatetime) {
		this.rv_registDatetime = rv_registDatetime;
	}
	public String getRv_user_name() {
		return rv_user_name;
	}
	public void setRv_user_name(String rv_user_name) {
		this.rv_user_name = rv_user_name;
	}
	public String getRv_mobility_name() {
		return rv_mobility_name;
	}
	public void setRv_mobility_name(String rv_mobility_name) {
		this.rv_mobility_name = rv_mobility_name;
	}
	
}
