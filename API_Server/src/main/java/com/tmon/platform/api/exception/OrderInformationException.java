package com.tmon.platform.api.exception;

/**
 * OrderInformationExcpetion
 * 
 * @author 구도원
 *
 */
public class OrderInformationException extends Exception {

	private int errCode;

	public OrderInformationException(int errCode, String errMsg) {
		super(errMsg);
		this.errCode = errCode;
	}
}
