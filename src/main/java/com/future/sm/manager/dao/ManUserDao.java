package com.future.sm.manager.dao;

import com.future.sm.common.vo.CheckBox;
import com.future.sm.common.vo.UserDeptVo;
import com.future.sm.manager.pojo.ManUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ManUserDao {
    List<UserDeptVo> findPageObjects(@Param("username") String username,
                                     @Param("start")int start,
                                     @Param("pageSize")int pageSize);

    @Select("select count(*) from sys_users")
    int getRowCount();

    @Update("update sys_users set valid=#{valid},modifiedUser=#{modifiedUser},modifiedTime=now() where id=#{id}")
    int updateValidById(Integer id,Integer valid,String modifiedUser);

    int saveObject(ManUser manUser);

    @Select("select max(id) from sys_users")
    int getMaxId();

    UserDeptVo findObjectById(Integer id);

    int updateObject(ManUser manUser);
}
