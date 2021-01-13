package com.future.sm.manager.dao;

import com.future.sm.common.vo.RoleMenuVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import com.future.sm.manager.pojo.ManRoleMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ManRoleMenuDao {
	int deleteObjectsBymenuId(Integer id);

	void insertObject(ManRoleMenu manRoleMenu);

	//因为这会有多个menuId，为了不创建多个ManRoleMenu对象来封装数据，这里采用直接存储的方式
	int doSaveObjects(@Param("roleId") Integer roleId,
					   @Param("menuIds") Integer... menuIds);

	@Select("select menu_id from sys_role_menus where role_id = #{roleId}")
	List<Integer> findObjectByRoleId(Integer roleId);

	@Delete("delete from sys_role_menus where role_id = #{roleId}")
	int deleteObjectsByRoleId(Integer roleId);

	List<Integer> findMenuIdsByRoleIds(Integer... roleIds);
}
