package kr.co.rentwheel.dto;

public class BookmarkDto {
	private String bm_user_id;
	private int bm_mobility_id;
	private int bm_flag;
	
	public String getBm_user_id() {
		return bm_user_id;
	}
	public void setBm_user_id(String bm_user_id) {
		this.bm_user_id = bm_user_id;
	}
	public int getBm_mobility_id() {
		return bm_mobility_id;
	}
	public void setBm_mobility_id(int bm_mobility_id) {
		this.bm_mobility_id = bm_mobility_id;
	}
	public int getBm_flag() {
		return bm_flag;
	}
	public void setBm_flag(int bm_flag) {
		this.bm_flag = bm_flag;
	}
	
	
}
