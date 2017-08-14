package com.tmon.platform.api.dto;

import java.util.Date;

/**
 * SnapShotDto
 * 
 * @author 구도원
 * 
 *         UI Server측의 요구에 따라 "yyyy-MM-dd HH:mm:ss" String 형식으로 결과 출력
 *         (snapshotTime, snapshotStartTime, snapshotEndTime)
 *
 */
public class SnapShotDto {

	private Date snapshotTime;
	private Date snapshotStartTime;
	private Date snapshotEndTime;
	private int snapshotOrderCount;
	private int snapshotCancelCount;

	public Date getSnapshotTime() {
		return snapshotTime;
	}

	public void setSnapshotTime(Date snapshotTime) {
		this.snapshotTime = snapshotTime;
	}

	public Date getSnapshotStartTime() {
		return snapshotStartTime;
	}

	public void setSnapshotStartTime(Date snapshotStartTime) {
		this.snapshotStartTime = snapshotStartTime;
	}

	public Date getSnapshotEndTime() {
		return snapshotEndTime;
	}

	public void setSnapshotEndTime(Date snapshotEndTime) {
		this.snapshotEndTime = snapshotEndTime;
	}

	public int getSnapshotOrderCount() {
		return snapshotOrderCount;
	}

	public void setSnapshotOrderCount(int snapshotOrderCount) {
		this.snapshotOrderCount = snapshotOrderCount;
	}

	public int getSnapshotCancelCount() {
		return snapshotCancelCount;
	}

	public void setSnapshotCancelCount(int snapshotCancelCount) {
		this.snapshotCancelCount = snapshotCancelCount;
	}

}
