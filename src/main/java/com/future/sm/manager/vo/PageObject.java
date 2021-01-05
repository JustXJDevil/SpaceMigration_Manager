package com.future.sm.manager.vo;

import java.util.List;
/**
 * 用来封装从数据库查出来的数据
 * @author Administrator
 *
 * @param <T>
 */
public class PageObject<T> {
	private Integer pageCurrent = 1;		//当前页码值
	private Integer pageSize = 3;			//页面大小
	private Integer rowCount = 0;			//总行数（查询得来）
	private Integer pageCount = 0;			//总页数（计算得来）
	private List<T> records;				//当前页记录
	public Integer getPageCurrent() {
		return pageCurrent;
	}
	public void setPageCurrent(Integer pageCurrent) {
		this.pageCurrent = pageCurrent;
	}
	public Integer getRowCount() {
		return rowCount;
	}
	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public List<T> getRecords() {
		return records;
	}
	public void setRecords(List<T> records) {
		this.records = records;
	}
	
}
