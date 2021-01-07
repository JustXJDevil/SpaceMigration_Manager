package com.future.sm.manager.dao;

import com.future.sm.common.vo.UserDeptVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ManUserDao {
    List<UserDeptVo> findPageObjects(@Param("username") String username,
                                     @Param("start")int start,
                                     @Param("pageSize")int pageSize);

    @Select("select count(*) from sys_users")
    int getRowCount();
}
