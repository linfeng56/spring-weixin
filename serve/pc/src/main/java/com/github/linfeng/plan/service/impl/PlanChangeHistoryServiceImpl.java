package com.github.linfeng.plan.service.impl;

import com.github.linfeng.plan.entity.PlanChangeHistory;
import com.github.linfeng.plan.mapper.PlanChangeHistoryMapper;
import com.github.linfeng.plan.service.IPlanChangeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanChangeHistoryServiceImpl implements IPlanChangeHistoryService {

    @Autowired
    private PlanChangeHistoryMapper changeHistoryMapper;

    @Override
    public List<PlanChangeHistory> listByWeekId(Integer weekId) {
        return changeHistoryMapper.listByWeekId(weekId);
    }

    @Override
    public Integer add(PlanChangeHistory history) {
        return changeHistoryMapper.add(history);
    }
}
