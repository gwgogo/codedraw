package com.tmon.platform.api.exception;

public class ProductException extends CustomException {
	public ProductException(int errCode, String errMsg) {
		super(errCode, errMsg);
	}
}
