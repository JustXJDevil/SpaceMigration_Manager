package com.future.sm.manager.service;

import com.future.sm.common.vo.UserDeptVo;
import com.future.sm.manager.vo.PageObject;

import java.util.List;

public interface ManUserService {
    PageObject findPageObjects(Integer pageCurrent, String username);
}
