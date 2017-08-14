package com.tmon.platform.api.exception;

public class RangeNotSatisfyException extends AbstractCustomException{
	public RangeNotSatisfyException(int errCode, String errMsg) {
		super(errCode, errMsg);
	}

}
