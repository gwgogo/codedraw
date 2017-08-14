package com.tmon.platform.api.exception;

public class SQLCustomException extends AbstractCustomException{
	public SQLCustomException(int errCode, String errMsg) {
		super(errCode, errMsg);
	}
}
