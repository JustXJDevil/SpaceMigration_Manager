package com.future.sm.manager.vo;

public class JsonResult {
	//状态码
	private int state = 1;	//1表示sucess，0表示error
	//状态信息
	private String message = "OK";
	//数据
	private Object data;
	public JsonResult(){}
	public JsonResult(String message) {
		this.message = message;
	}
	//由全局异常类传递异常信息。
	public JsonResult(Throwable t) {
		this.state = 0;
		this.message = t.getMessage();
	}
	public JsonResult(Object data) {
		this.data = data;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}
