package com.future.sm.manager.service;

import com.future.sm.common.vo.CheckBox;
import com.future.sm.common.vo.UserDeptVo;
import com.future.sm.manager.pojo.ManUser;
import com.future.sm.manager.vo.PageObject;

import java.util.List;
import java.util.Map;

public interface ManUserService {
    PageObject findPageObjects(Integer pageCurrent, String username);

    void updateValidById(Integer id,Integer valid,String modifiedUser);

    void saveObject(ManUser manUser,Integer... roleIds);

    Map<String,Object> findObjectById(Integer id);

    void updateObject(ManUser manUser,Integer... roleIds);
}
