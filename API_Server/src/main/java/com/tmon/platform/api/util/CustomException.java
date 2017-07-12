package com.tmon.platform.api.util;

public class CustomException extends Exception{
	
	private String errMsg;
	public CustomException(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
}
