package com.github.linfeng.plan.service;

import com.github.linfeng.plan.entity.Role;

public interface IRoleService {

    Role createRole(Role role);

    void deleteRole(Long roleId);

    /**
     * 关联角色和权限
     *
     * @param roleId        角色编号
     * @param permissionIds 权限编号
     */
    void correlationPermissions(Long roleId, Long... permissionIds);

    /**
     * 解除角色和权限的关联
     *
     * @param roleId        角色编号
     * @param permissionIds 权限编号
     */
    void unCorrelationPermissions(Long roleId, Long... permissionIds);
}
