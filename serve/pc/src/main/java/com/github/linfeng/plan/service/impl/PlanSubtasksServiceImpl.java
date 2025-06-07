package com.github.linfeng.plan.service.impl;

import com.github.linfeng.plan.entity.PlanSubtasks;
import com.github.linfeng.plan.mapper.PlanSubtasksMapper;
import com.github.linfeng.plan.service.IPlanSubtasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanSubtasksServiceImpl implements IPlanSubtasksService {

    @Autowired
    private PlanSubtasksMapper subtasksMapper;

    @Override
    public List<PlanSubtasks> listByWeekId(Integer weekId) {
        return subtasksMapper.listByWeekId(weekId);
    }

    @Override
    public PlanSubtasks getById(Integer id) {
        return subtasksMapper.getById(id);
    }

    @Override
    public Integer add(PlanSubtasks subtask) {
        return subtasksMapper.add(subtask);
    }

    @Override
    public Integer update(Integer id, PlanSubtasks subtask) {
        return subtasksMapper.update(id, subtask);
    }

    @Override
    public Integer delete(Integer id) {
        return subtasksMapper.delete(id);
    }
}
