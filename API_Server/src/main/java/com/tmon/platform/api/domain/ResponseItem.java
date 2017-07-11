package com.tmon.platform.api.domain;

public class ResponseItem {
	private String code;
	private String msg;
	
	public ResponseItem() {
		this.code = "200";
		this.msg = "success";
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
