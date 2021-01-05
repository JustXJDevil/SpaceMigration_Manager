package com.future.sm.common.vo;

import java.io.Serializable;
/**
 * 用于封装树结构的节点信息
 * */
public class Node implements Serializable{
	private static final long serialVersionUID = 1L;
	//节点id
	private int id;
	//节点名称
	private String name;
	//上级节点id
	private int parentId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

}
