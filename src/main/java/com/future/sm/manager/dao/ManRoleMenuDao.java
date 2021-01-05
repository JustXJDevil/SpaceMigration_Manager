package com.future.sm.manager.dao;

import org.apache.ibatis.annotations.Mapper;

import com.future.sm.manager.pojo.ManRoleMenu;

@Mapper
public interface ManRoleMenuDao {
	int deleteObjectsBymenuId(Integer id);

	void insertObject(ManRoleMenu manRoleMenu);
}
