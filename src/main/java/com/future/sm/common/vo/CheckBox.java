package com.future.sm.common.vo;

import java.io.Serializable;

/**
 * 封装角色id和name,在页面底部，用于创建用户时直接勾选角色
 */
public class CheckBox implements Serializable {
    private static final long serialVersionUID = -9053663834776996375L;
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
