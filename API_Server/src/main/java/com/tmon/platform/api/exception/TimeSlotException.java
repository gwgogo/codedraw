package com.tmon.platform.api.exception;

public class TimeSlotException extends Exception {

	private int errCode;

	public TimeSlotException(int errCode, String errMsg) {
		super(errMsg);
		this.errCode = errCode;
	}
}
