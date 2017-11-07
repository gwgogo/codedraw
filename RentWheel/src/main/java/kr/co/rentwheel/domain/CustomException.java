package kr.co.rentwheel.domain;

import java.util.HashMap;
import java.util.Map;

public class CustomException extends Exception{
	private String result;
	private String msg;
	public CustomException(String result, String msg ) {
		this.result = result;
		this.msg = msg;
	}
	
	public Map<String, String> getErrorStatus() {
		Map<String, String> map = new HashMap();
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}
}
