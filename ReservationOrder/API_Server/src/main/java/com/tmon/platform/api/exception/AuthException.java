package com.tmon.platform.api.exception;

public class AuthException extends AbstractCustomException{
	public AuthException(int errCode, String errMsg) {
		super(errCode, errMsg);
	}
}
