package com.future.sm.manager.dao;

import com.future.sm.manager.pojo.ManDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ManDeptDao {
    @Select("select * from sys_depts where id = #{id}")
    ManDept findObjectById(Integer id);
}
