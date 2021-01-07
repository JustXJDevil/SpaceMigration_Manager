package com.future.sm.manager.dao;

import com.future.sm.common.vo.RoleMenuVo;
import com.future.sm.manager.pojo.ManRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ManRoleDao {

    Integer gerRowCounts(String name);

    List<ManRole> findPageObjects(
            @Param("name") String name,
            @Param("start") int start,
            @Param("pageSize")int pageSize
    );

    void doSaveObject(ManRole manRole);

    @Select("select max(id) from sys_roles")
    int getMaxId();

    @Select("select id,name,note from sys_roles where id = #{id}")
    RoleMenuVo findObjectById(Integer id);

    int updateObject(ManRole manRole);

    @Delete("delete from sys_roles where id = #{id}")
    int deleteObjectById(Integer id);
}
