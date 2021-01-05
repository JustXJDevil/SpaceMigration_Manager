package com.future.sm.manager.pojo;

import java.util.Date;

public class ManLog {
	private Integer id;
	private String username;
	private String opration;	//用户操作
	private String method;		//请求方法
	private String params;		//请求参数
	private Long time;			//执行时长（毫秒）
	private String ip;			//IP地址
	private Date createdTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOpration() {
		return opration;
	}
	public void setOpration(String opration) {
		this.opration = opration;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getCreateTime() {
		return createdTime;
	}
	public void setCreateTime(Date createTime) {
		this.createdTime = createTime;
	}
	
	
}
