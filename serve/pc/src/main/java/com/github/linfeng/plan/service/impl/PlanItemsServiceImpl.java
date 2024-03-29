package com.github.linfeng.plan.service.impl;

import java.util.List;
import com.github.linfeng.plan.entity.PlanItems;
import com.github.linfeng.plan.mapper.PlanItemsMapper;
import com.github.linfeng.plan.service.IPlanItemsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务实现类.
 *
 * @author 黄麟峰
 */
@Service
public class PlanItemsServiceImpl implements IPlanItemsService {

    @Autowired
    private PlanItemsMapper planItemsMapper;

    @Override
    public List<PlanItems> list() {
        return planItemsMapper.list(null, null);
    }

    @Override
    public PlanItems getById(Integer id) {
        return planItemsMapper.getById(id);
    }

    @Override
    public Integer add(PlanItems item) {
        return planItemsMapper.add(item);
    }

    @Override
    public List<PlanItems> listByWeek(Integer weekId, Integer searchUserId, String searchText) {
        return planItemsMapper.listByWeek(weekId, searchUserId, searchText);
    }

    @Override
    public Integer edit(Integer id, PlanItems item) {
        return planItemsMapper.edit(id, item);
    }

    @Override
    public List<PlanItems> list(Long start, Long end) {
        return planItemsMapper.list(start, end);
    }

    @Override
    public Integer cnt() {
        return planItemsMapper.count();
    }
}
