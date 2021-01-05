package com.future.sm.manager.pojo;

import java.io.Serializable;
import java.util.Date;

public class ManRole implements Serializable {
    private static final long serialVersionUID = -6131574627564318805L;
    private Integer id;
    private String name;
    private String note;
    private Date createdTime;
    private Date modifiedTime;
    private String createdUser;
    private String modifiedUser;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }
}
