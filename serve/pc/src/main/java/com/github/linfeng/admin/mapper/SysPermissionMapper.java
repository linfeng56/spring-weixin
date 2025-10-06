package com.github.linfeng.admin.mapper;

import com.github.linfeng.admin.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysPermissionMapper {

    List<SysPermission> list();

    SysPermission getById(@Param("id") Integer id);

    List<SysPermission> listByRoleId(@Param("roleId") Integer roleId);
}
