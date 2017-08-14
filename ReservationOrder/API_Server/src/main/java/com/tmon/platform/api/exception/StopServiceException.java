package com.tmon.platform.api.exception;

public class StopServiceException extends AbstractCustomException {

	public StopServiceException(int errCode, String errMsg) {
		super(errCode, errMsg);
	}
}
