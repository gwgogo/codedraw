package com.tmon.platform.api.exception;

/**
 * TimeSlotException
 * 
 * @author 구도원
 *
 */
public class TimeSlotException extends Exception {

	private int errCode;

	public TimeSlotException(int errCode, String errMsg) {
		super(errMsg);
		this.errCode = errCode;
	}
}
