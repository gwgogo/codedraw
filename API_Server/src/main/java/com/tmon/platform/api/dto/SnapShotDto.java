package com.tmon.platform.api.dto;

import java.util.Date;

public class SnapShotDto {

	private Date snapshot_time;
	private Date snapshot_start_time;
	private Date snapshot_end_time;
	private int snapshot_order_count;
	private int snapshot_cancel_count;

	public Date getSnapshot_time() {
		return snapshot_time;
	}

	public void setSnapshot_time(Date snapshot_time) {
		this.snapshot_time = snapshot_time;
	}

	public Date getSnapshot_start_time() {
		return snapshot_start_time;
	}

	public void setSnapshot_start_time(Date snapshot_start_time) {
		this.snapshot_start_time = snapshot_start_time;
	}

	public Date getSnapshot_end_time() {
		return snapshot_end_time;
	}

	public void setSnapshot_end_time(Date snapshot_end_time) {
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
