package com.tmon.platform.api.exception;

public class PreConditionException extends AbstractCustomException{
	public PreConditionException(int errCode, String errMsg) {
		super(errCode, errMsg);
	}
}
