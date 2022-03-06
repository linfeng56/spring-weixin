package com.github.linfeng.plan.entity;

import java.io.Serializable;

public class UserRole implements Serializable {

    private Long userId;
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserRole userRole = (UserRole) o;

        if (roleId != null) {
            if (!roleId.equals(userRole.roleId)) {
                return false;
            }
        } else {
            if (userRole.roleId != null) {
                return false;
            }
        }

        if (userId != null) {
            if (!userId.equals(userRole.userId)) {
                return false;
            }
        } else {
            if (userRole.userId != null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId != null ? roleId.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);

        return result;
    }
}
