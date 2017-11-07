package kr.co.rentwheel.dto;

public class ReservationDto {
	private int rs_id;
	private String rs_user_id;
	private int rs_mobility_id;
	private int rs_price;
	private String rs_start_time;
	private String rs_end_time;
	private String rs_mobility_name;
	private String rs_mobility_img;
	private String rs_company_id;
	private String rs_company_name;
	private int rs_flag;	// 0: 예약완료 , 1:이용완료, 2:예약취소
	private String rs_flag_string;
	private String rs_company_address;
	private String rs_brand_name;
	private String rs_category_name;
	
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
	public int getRs_price() {
		return rs_price;
	}
	public void setRs_price(int rs_price) {
		this.rs_price = rs_price;
	}
	public String getRs_mobility_name() {
		return rs_mobility_name;
	}
	public void setRs_mobility_name(String rs_mobility_name) {
		this.rs_mobility_name = rs_mobility_name;
	}
	public String getRs_mobility_img() {
		return rs_mobility_img;
	}
	public void setRs_mobility_img(String rs_mobility_img) {
		this.rs_mobility_img = rs_mobility_img;
	}
	public String getRs_company_id() {
		return rs_company_id;
	}
	public void setRs_company_id(String rs_company_id) {
		this.rs_company_id = rs_company_id;
	}
	public String getRs_company_name() {
		return rs_company_name;
	}
	public void setRs_company_name(String rs_company_name) {
		this.rs_company_name = rs_company_name;
	}
	public int getRs_flag() {
		return rs_flag;
	}
	public void setRs_flag(int rs_flag) {
		this.rs_flag = rs_flag;
	}
	public String getRs_flag_string() {
		return rs_flag_string;
	}
	public void setRs_flag_string(String rs_flag_string) {
		this.rs_flag_string = rs_flag_string;
	}
	public String getRs_company_address() {
		return rs_company_address;
	}
	public void setRs_company_address(String rs_company_address) {
		this.rs_company_address = rs_company_address;
	}
	public String getRs_brand_name() {
		return rs_brand_name;
	}
	public void setRs_brand_name(String rs_brand_name) {
		this.rs_brand_name = rs_brand_name;
	}
	public String getRs_category_name() {
		return rs_category_name;
	}
	public void setRs_category_name(String rs_category_name) {
		this.rs_category_name = rs_category_name;
	}
	
	
}
