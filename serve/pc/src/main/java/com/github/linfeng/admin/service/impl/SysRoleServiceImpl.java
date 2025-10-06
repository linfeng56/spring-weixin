package com.github.linfeng.admin.service.impl;

import com.github.linfeng.admin.entity.SysRole;
import com.github.linfeng.admin.mapper.SysRoleMapper;
import com.github.linfeng.admin.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements ISysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Override
    public List<SysRole> list() {
        return roleMapper.list();
    }

    @Override
    public SysRole getById(Integer id) {
        return roleMapper.getById(id);
    }

    @Override
    public Integer add(SysRole role) {
        return roleMapper.add(role);
    }

    @Override
    public Integer update(Integer id, SysRole role) {
        return roleMapper.update(id, role);
    }

    @Override
    public Integer delete(Integer id) {
        return roleMapper.delete(id);
    }
}
