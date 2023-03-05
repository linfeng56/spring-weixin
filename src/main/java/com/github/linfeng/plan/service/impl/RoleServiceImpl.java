package com.github.linfeng.plan.service.impl;

import java.util.List;
import com.github.linfeng.plan.entity.Role;
import com.github.linfeng.plan.mapper.RoleMapper;
import com.github.linfeng.plan.service.IRoleService;

import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {

    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public Role createRole(Role role) {
        roleMapper.createRole(role);
        return null;
    }

    @Override
    public void deleteRole(Long roleId) {
        roleMapper.deleteRole(roleId);
    }

    @Override
    public void correlationPermissions(Long roleId, Long... permissionIds) {
        roleMapper.correlationPermissions(roleId, permissionIds);
    }

    @Override
    public void unCorrelationPermissions(Long roleId, Long... permissionIds) {
        roleMapper.unCorrelationPermissions(roleId, permissionIds);
    }

    @Override
    public List<Role> list() {
        return roleMapper.list();
    }

    @Override
    public Role getById(Integer id) {
        return roleMapper.getById(id);
    }

    @Override
    public Integer update(Integer id, Role role) {
        return roleMapper.update(id, role);
    }
}
