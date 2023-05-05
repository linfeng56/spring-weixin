package com.github.linfeng.plan.service;

import com.github.linfeng.plan.entity.Permission;

public interface IPermissionService {

    Permission createPermission(Permission permission);

    void deletePermission(Long permissionId);
}
