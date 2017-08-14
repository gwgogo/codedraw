package com.tmon.platform.api.exception;

public class NullCustomException extends AbstractCustomException{
	public NullCustomException(int errCode, String errMsg) {
		super(errCode, errMsg);
	}
}
