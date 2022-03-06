package com.github.linfeng.plan.entity;

import java.io.Serializable;

public class RolePermssion implements Serializable {

    private Long roleId;
    private Long permssionId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermssionId() {
        return permssionId;
    }

    public void setPermssionId(Long permssionId) {
        this.permssionId = permssionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RolePermssion that = (RolePermssion) o;

        if (permssionId != null) {
            if (!permssionId.equals(that.permssionId)) {
                return false;
            }
        } else {
            if (that.permssionId != null) {
                return false;
            }
        }

        if (roleId != null) {
            if (!roleId.equals(that.roleId)) {
                return false;
            }
        } else {
            if (that.roleId != null) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = permssionId != null ? permssionId.hashCode() : 0;
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RolePermssion{");
        sb.append("roleId=").append(roleId);
        sb.append(", permssionId=").append(permssionId);
        sb.append('}');
        return sb.toString();
    }
}
