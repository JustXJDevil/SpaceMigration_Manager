package com.future.sm.manager.service;

import com.future.sm.manager.pojo.ManRole;
import com.future.sm.manager.vo.PageObject;

import java.util.List;

public interface ManRoleService {
    PageObject<ManRole> findPageObjects(String name, Integer pageCurrent);

}
