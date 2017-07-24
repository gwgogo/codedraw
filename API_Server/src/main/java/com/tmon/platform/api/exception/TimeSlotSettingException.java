package com.tmon.platform.api.exception;

/**
 * TimeSlotSettingException
 * 
 * @author 구도원
 *
 */
public class TimeSlotSettingException extends Exception {

	private int errCode;

	public TimeSlotSettingException(int errCode, String errMsg) {
		super(errMsg);
		this.errCode = errCode;
	}

}
