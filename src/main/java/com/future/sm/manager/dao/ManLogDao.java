package com.future.sm.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.future.sm.manager.pojo.ManLog;
@Mapper
public interface ManLogDao {
	public List<ManLog> findPageObject(
			@Param("username")String username,
			@Param("start")Integer start,
			@Param("pageSize")Integer pageSize);
	public Integer getRowCounts();
	
	public int doDeleteObjects(@Param("ids")Integer...ids);

	public int insertObject(ManLog manLog);
}
