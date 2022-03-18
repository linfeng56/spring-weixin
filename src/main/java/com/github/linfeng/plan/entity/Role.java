package com.github.linfeng.plan.entity;

import java.io.Serializable;

public class Role implements Serializable {

    private Long id;
    private String role;
    private String description;
    private Boolean available = Boolean.FALSE;

    public Role() {

    }

    public Role(String role, String description, Boolean available) {
        this.role = role;
        this.description = description;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
        Role roleCompare = (Role) o;
        if (id != null) {
            if (!id.equals(roleCompare.id)) {
                return false;
            }
        } else {
            if (roleCompare.id != null) {
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
        final StringBuilder sb = new StringBuilder("Role{");
        sb.append("id=").append(id);
        sb.append(", role='").append(role).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", available=").append(available);
        sb.append('}');
        return sb.toString();
    }
}
