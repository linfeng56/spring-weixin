package com.github.linfeng.admin.entity;

import java.io.Serializable;

public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer permId;

    private String permName;

    private String permKey;

    private Integer parentId;

    private Integer sort;

    private Integer status;

    public Integer getPermId() {
        return permId;
    }

    public void setPermId(Integer permId) {
        this.permId = permId;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

    public String getPermKey() {
        return permKey;
    }

    public void setPermKey(String permKey) {
        this.permKey = permKey;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
