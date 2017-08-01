package com.tmon.platform.api.dto;

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

	private String snapshotTime;
	private String snapshotStartTime;
	private String snapshotEndTime;
	private int snapshotOrderCount;
	private int snapshotCancelCount;

	public String getSnapshotTime() {
		return snapshotTime;
	}

	public void setSnapshotTime(String snapshotTime) {
		this.snapshotTime = snapshotTime;
	}

	public String getSnapshotStartTime() {
		return snapshotStartTime;
	}

	public void setSnapshotStartTime(String snapshotStartTime) {
		this.snapshotStartTime = snapshotStartTime;
	}

	public String getSnapshotEndTime() {
		return snapshotEndTime;
	}

	public void setSnapshotEndTime(String snapshotEndTime) {
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
