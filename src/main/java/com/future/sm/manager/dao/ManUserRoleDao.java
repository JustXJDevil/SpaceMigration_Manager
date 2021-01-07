package com.future.sm.manager.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ManUserRoleDao {
    @Delete("delete from sys_user_roles where role_id = #{roleId}")
    int deleteObjectsByRoleId(Integer roleId);
}
