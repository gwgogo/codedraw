package com.tmon.platform.api.exception;

public class AuthException extends CustomException{
	public AuthException(int errCode, String errMsg) {
		super(errCode, errMsg);
	}
}
