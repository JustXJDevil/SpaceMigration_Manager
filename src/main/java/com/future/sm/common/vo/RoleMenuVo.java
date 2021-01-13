package com.future.sm.common.vo;

import java.io.Serializable;
import java.util.List;

public class RoleMenuVo implements Serializable {
    private static final long serialVersionUID = 2192323693038290187L;
    Integer id;
    String name;
    String note;
    List<Integer> menuIds;

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Integer> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Integer> menuIds) {
        this.menuIds = menuIds;
    }

    @Override
    public String toString() {
        return "RoleMenuVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", menuId=" + menuIds +
                '}';
    }
}
