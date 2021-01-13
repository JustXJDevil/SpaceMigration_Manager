package com.future.sm.common.util;

import com.future.sm.manager.pojo.ManUser;
import org.apache.shiro.SecurityUtils;

public class ShiroUtils {
    public static ManUser getUser(){
        return (ManUser) SecurityUtils.getSubject().getPrincipal();
    }
}
