package com.github.linfeng.admin.service;

import com.github.linfeng.admin.entity.SysRole;

import java.util.List;

public interface ISysRoleService {

    List<SysRole> list();

    SysRole getById(Integer id);

    Integer add(SysRole role);

    Integer update(Integer id, SysRole role);

    Integer delete(Integer id);
}
