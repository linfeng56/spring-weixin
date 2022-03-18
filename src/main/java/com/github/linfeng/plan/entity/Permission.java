package com.github.linfeng.plan.entity;

import java.io.Serializable;

public class Permission implements Serializable {

    private Long id;
    private String permission;
    private String description;
    private Boolean available = Boolean.FALSE;

    public Permission() {
    }

    public Permission(String permission, String description, Boolean available) {
        this.permission = permission;
        this.description = description;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Permission that = (Permission) o;

        if (id != null) {
            if (id.equals(that.id)) {
                return false;
            }
        } else {
            if (that.id != null) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Permission{");
        sb.append("id=").append(id);
        sb.append(", permission='").append(permission).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", available=").append(available);
        sb.append('}');
        return sb.toString();
    }
}
