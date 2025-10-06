package com.github.linfeng.admin.mapper;

import com.github.linfeng.admin.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    List<SysRole> list();

    SysRole getById(@Param("id") Integer id);

    Integer add(@Param("role") SysRole role);

    Integer update(@Param("id") Integer id, @Param("role") SysRole role);

    Integer delete(@Param("id") Integer id);
}
