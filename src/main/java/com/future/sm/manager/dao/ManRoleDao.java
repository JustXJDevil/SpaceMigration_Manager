package com.future.sm.manager.dao;

import com.future.sm.manager.pojo.ManRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ManRoleDao {

    Integer gerRowCounts(String name);

    List<ManRole> findPageObjects(
            @Param("name") String name,
            @Param("start") int start,
            @Param("pageSize")int pageSize
    );
}
