package kr.co.rentwheel.domain;



public class ResponseItem {
    public String result;
    public String msg;
	
    public ResponseItem() {
    	this.result = "200";
    	this.msg = "success";
    }
    
    public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
    
}