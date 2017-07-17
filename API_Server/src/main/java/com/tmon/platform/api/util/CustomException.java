package com.tmon.platform.api.util;

public class CustomException extends Exception{

	private int errCode;
	public CustomException(int errCode, String errMsg ) {
		super(errMsg);
		this.errCode = errCode;
	}
}
