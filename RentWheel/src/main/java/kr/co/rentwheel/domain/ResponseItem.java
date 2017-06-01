package kr.co.rentwheel.domain;

import lombok.Data;

public @Data class ResponseItem {
    public String result="200";
    public String msg="success";
    
    /*public ResponseItem(String result, String msg){
    	this.result = result;
    	this.msg = msg;
    }*/
}