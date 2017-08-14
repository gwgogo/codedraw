package com.tmon.platform.batch.snapshot.model;

/**
 * SnapShotDto
 * 
 * @author 구도원
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
