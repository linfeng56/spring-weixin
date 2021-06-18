package com.github.linfeng.plan.service.impl;

import java.util.List;
import com.github.linfeng.plan.entity.PlanWeekSummary;
import com.github.linfeng.plan.entity.PlanWeeks;
import com.github.linfeng.plan.mapper.PlanWeekSummaryMapper;
import com.github.linfeng.plan.service.IPlanWeeksSummaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务实现类.
 *
 * @author 黄麟峰
 */
@Service
public class PlanWeeksSummaryServiceImpl implements IPlanWeeksSummaryService {

    @Autowired
    private PlanWeekSummaryMapper planWeekSummaryMapper;

    @Override
    public List<PlanWeekSummary> list() {
        return planWeekSummaryMapper.list();
    }

    @Override
    public PlanWeekSummary getByWeekId(Integer weekId) {
        return planWeekSummaryMapper.getByWeekId(weekId);
    }

    @Override
    public PlanWeekSummary getByUserId(Integer userId) {
        return planWeekSummaryMapper.getByUserId(userId);
    }

    @Override
    public Integer add(PlanWeekSummary weeks) {
        return planWeekSummaryMapper.add(weeks);
    }

    @Override
    public List<PlanWeekSummary> list(String searchText) {
        return planWeekSummaryMapper.listBySummary(searchText);
    }

    @Override
    public Integer update(Integer id, PlanWeekSummary week) {
        return planWeekSummaryMapper.update(id, week);
    }

    @Override
    public PlanWeekSummary getById(Integer id) {
        return planWeekSummaryMapper.getById(id);
    }
}
