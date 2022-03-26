package com.github.linfeng.plan.mapper;

import com.github.linfeng.plan.entity.Role;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMapper {

    /**
     * 创建角色
     *
     * @param role 角色
     */
    void createRole(@Param("role") final Role role);

    /**
     * 删除角色以及角色关联数据
     *
     * @param roleId 角色编号
     */
    void deleteRole(@Param("roleId") Long roleId);

    /**
     * 关联角色与权限
     *
     * @param roleId        角色
     * @param permissionIds 权限编号
     */
    void correlationPermissions(@Param("roleId") Long roleId, @Param("permissionIds") Long... permissionIds);

    /**
     * 解除角色与权限的关联
     *
     * @param roleId        角色
     * @param permissionIds 权限编号
     */
    void unCorrelationPermissions(@Param("roleId") Long roleId, @Param("permissionIds") Long... permissionIds);
}