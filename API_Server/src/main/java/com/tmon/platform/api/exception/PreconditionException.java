package com.tmon.platform.api.exception;

public class PreconditionException extends CustomException{
	
	public PreconditionException(int errCode, String errMsg ) {
		super(errCode, errMsg);
	}

}
