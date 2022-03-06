package com.github.linfeng.plan.mapper;

import com.github.linfeng.plan.entity.Permission;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionMapper {

    void createPermission(@Param("permission") final Permission permission);

    void deletePermission(@Param("permissionId") Long permissionId);
}
