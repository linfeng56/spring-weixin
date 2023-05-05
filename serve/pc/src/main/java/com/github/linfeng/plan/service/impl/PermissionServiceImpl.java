package com.github.linfeng.plan.service.impl;

import com.github.linfeng.plan.entity.Permission;
import com.github.linfeng.plan.mapper.PermissionMapper;
import com.github.linfeng.plan.service.IPermissionService;

import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements IPermissionService {

    private final PermissionMapper permissionMapper;

    public PermissionServiceImpl(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public Permission createPermission(Permission permission) {
        permissionMapper.createPermission(permission);
        return null;
    }

    @Override
    public void deletePermission(Long permissionId) {
        permissionMapper.deletePermission(permissionId);
    }
}
