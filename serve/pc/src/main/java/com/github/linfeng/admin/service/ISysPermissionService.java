package com.github.linfeng.admin.service;

import com.github.linfeng.admin.entity.SysPermission;

import java.util.List;

public interface ISysPermissionService {

    List<SysPermission> list();

    SysPermission getById(Integer id);

    List<SysPermission> listByRoleId(Integer roleId);
}
