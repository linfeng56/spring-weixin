package com.github.linfeng.plan.service;

import java.util.List;
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

    /**
     * 角色列表
     */
    List<Role> list();

    /**
     * 通过主键获取角色信息
     *
     * @param id 角色编号
     * @return 角色信息
     */
    Role getById(Integer id);

    /**
     * 更新角色信息
     *
     * @param id   角色编号
     * @param role 角色信息
     * @return 受影响条数
     */
    Integer update(Integer id, Role role);
}
