package com.tmon.platform.api.exception;

/**
 * OrderInformationExcpetion
 * 
 * @author 구도원
 *
 */
public class DateFormatException extends AbstractCustomException {

	public DateFormatException(int errCode, String errMsg) {
		super(errCode, errMsg);
	}
}
