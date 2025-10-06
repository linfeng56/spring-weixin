package com.github.linfeng.admin.service.impl;

import com.github.linfeng.admin.entity.SysPermission;
import com.github.linfeng.admin.mapper.SysPermissionMapper;
import com.github.linfeng.admin.service.ISysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPermissionServiceImpl implements ISysPermissionService {

    @Autowired
    private SysPermissionMapper permissionMapper;

    @Override
    public List<SysPermission> list() {
        return permissionMapper.list();
    }

    @Override
    public SysPermission getById(Integer id) {
        return permissionMapper.getById(id);
    }

    @Override
    public List<SysPermission> listByRoleId(Integer roleId) {
        return permissionMapper.listByRoleId(roleId);
    }
}
