package com.future.sm.manager.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ManUserRoleDao {
    @Delete("delete from sys_user_roles where role_id = #{roleId}")
    int deleteObjectsByRoleId(Integer roleId);

    int saveObjects(Integer userId,Integer... roleIds);

    @Select("select role_id from sys_user_roles where user_id=#{userId}")
    List<Integer> findRoleIdsByUserId(Integer userId);

    @Delete("delete from sys_user_roles where user_id=#{userId}")
    int deleteObjectsByUserId(int userId);
}
