package com.tmon.platform.api.dto;

/**
 * SnapShotDto
 * 
 * @author 구도원
 * 
 *         UI Server측의 요구에 따라 "yyyy-MM-dd HH:mm:ss" String 형식으로 결과 출력
 *         (snapshot_time, snapshot_start_time, snapshot_end_time)
 *
 */
public class SnapShotDto {

	private String snapshot_time;
	private String snapshot_start_time;
	private String snapshot_end_time;
	private int snapshot_order_count;
	private int snapshot_cancel_count;

	public String getSnapshot_time() {
		return snapshot_time;
	}

	public void setSnapshot_time(String snapshot_time) {
		this.snapshot_time = snapshot_time;
	}

	public String getSnapshot_start_time() {
		return snapshot_start_time;
	}

	public void setSnapshot_start_time(String snapshot_start_time) {
		this.snapshot_start_time = snapshot_start_time;
	}

	public String getSnapshot_end_time() {
		return snapshot_end_time;
	}

	public void setSnapshot_end_time(String snapshot_end_time) {
		this.snapshot_end_time = snapshot_end_time;
	}

	public int getSnapshot_order_count() {
		return snapshot_order_count;
	}

	public void setSnapshot_order_count(int snapshot_order_count) {
		this.snapshot_order_count = snapshot_order_count;
	}

	public int getSnapshot_cancel_count() {
		return snapshot_cancel_count;
	}

	public void setSnapshot_cancel_count(int snapshot_cancel_count) {
		this.snapshot_cancel_count = snapshot_cancel_count;
	}

}
