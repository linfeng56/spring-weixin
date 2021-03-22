package com.github.linfeng.plan.service.impl;

import java.util.List;
import com.github.linfeng.plan.entity.PlanUsers;
import com.github.linfeng.plan.mapper.PlanUsersMapper;
import com.github.linfeng.plan.service.IPlanUsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务实现类.
 *
 * @author 黄麟峰
 */
@Service
public class PlanUsersServiceImpl implements IPlanUsersService {

    @Autowired
    private PlanUsersMapper planUsersMapper;

    @Override
    public List<PlanUsers> list() {
        return planUsersMapper.list();
    }

    @Override
    public PlanUsers getById(Integer id) {
        return planUsersMapper.getById(id);
    }
}
