package com.future.sm.manager.service;

import com.future.sm.common.vo.RoleMenuVo;
import com.future.sm.manager.pojo.ManRole;
import com.future.sm.manager.vo.PageObject;

import java.util.List;

public interface ManRoleService {
    PageObject<ManRole> findPageObjects(String name, Integer pageCurrent);

    void doSaveObject(ManRole manRole,Integer... menuIds);

    RoleMenuVo findObjectById(Integer id);

    void updateObject(ManRole manRole,Integer... menuIds);

    void deleteObjectById(Integer id);
}
