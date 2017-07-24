package com.tmon.platform.api.exception;

import java.util.HashMap;
import java.util.Map;

public class CustomException extends Exception{

	private int errCode;
	private String errMsg;
	public CustomException(int errCode, String errMsg ) {
		this.errMsg = errMsg;
		this.errCode = errCode;
	}
	
	public Map<String, String> getErrorStatus() {
		Map<String, String> map = new HashMap();
		map.put("errCode", String.valueOf(errCode));
		map.put("errMsg", errMsg);
		return map;
	}
}
