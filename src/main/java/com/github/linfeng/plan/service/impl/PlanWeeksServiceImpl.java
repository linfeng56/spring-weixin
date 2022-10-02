package com.github.linfeng.plan.service.impl;

import java.util.List;
import com.github.linfeng.plan.entity.PlanWeeks;
import com.github.linfeng.plan.mapper.PlanWeeksMapper;
import com.github.linfeng.plan.service.IPlanWeeksService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务实现类.
 *
 * @author 黄麟峰
 */
@Service
public class PlanWeeksServiceImpl implements IPlanWeeksService {

    @Autowired
    private PlanWeeksMapper planWeeksMapper;

    @Override
    public List<PlanWeeks> list() {
        return planWeeksMapper.list();
    }

    @Override
    public PlanWeeks getById(Integer id) {
        return planWeeksMapper.getById(id);
    }

    @Override
    public Integer add(PlanWeeks weeks) {
        return planWeeksMapper.add(weeks);
    }

    @Override
    public List<PlanWeeks> list(String searchText) {
        return planWeeksMapper.listByTitle(searchText);
    }

    @Override
    public Integer update(Integer id, PlanWeeks week) {
        return planWeeksMapper.update(id, week);
    }

    @Override
    public Integer updateSummary(Integer id, String summary, Long summaryDate) {
        return planWeeksMapper.updateSummary(id, summary, summaryDate);
    }

    @Override
    public Integer count() {
        return planWeeksMapper.count();
    }
}
