package com.tmon.platform.api.exception;

public class UserException extends CustomException{
	
	public UserException(int errCode, String errMsg ) {
		super(errCode, errMsg);
	}

}
