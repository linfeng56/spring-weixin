package com.github.linfeng.plan.mapper;

import java.util.List;
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

    /**
     * 角色列表
     *
     * @return 角色列表
     */
    List<Role> list();

    /**
     * 获取指定编号的角色
     *
     * @param id 角色编号
     * @return 角色
     */
    Role getById(Integer id);

    /**
     * 更新角色信息
     *
     * @param id   角色编号
     * @param role 角色信息
     * @return 受影响条数
     */
    Integer update(@Param("id") Integer id, @Param("r") Role role);

    /**
     * 禁用角色
     *
     * @param id 角色编号
     * @return 禁用记录数
     */
    Integer lock(@Param("id") Integer id);

    /**
     * 启用角色
     *
     * @param id 角色编号
     * @return 启用记录数
     */
    Integer unlock(@Param("id") Integer id);
}
