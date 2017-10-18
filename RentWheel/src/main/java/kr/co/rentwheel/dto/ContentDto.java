package kr.co.rentwheel.dto;

public class ContentDto {
	private int ct_id;	
	private int ct_category;	// 1:이벤트배너 , 2:추천
	private String ct_msg;
	private String ct_img;
	public int getCt_id() {
		return ct_id;
	}
	public void setCt_id(int ct_id) {
		this.ct_id = ct_id;
	}
	public int getCt_category() {
		return ct_category;
	}
	public void setCt_category(int ct_category) {
		this.ct_category = ct_category;
	}
	public String getCt_msg() {
		return ct_msg;
	}
	public void setCt_msg(String ct_msg) {
		this.ct_msg = ct_msg;
	}
	public String getCt_img() {
		return ct_img;
	}
	public void setCt_img(String ct_img) {
		this.ct_img = ct_img;
	}
	
}
