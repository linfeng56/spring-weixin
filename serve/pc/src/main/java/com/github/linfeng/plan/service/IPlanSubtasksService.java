package com.github.linfeng.plan.service;

import com.github.linfeng.plan.entity.PlanSubtasks;

import java.util.List;

public interface IPlanSubtasksService {

    List<PlanSubtasks> listByWeekId(Integer weekId);

    PlanSubtasks getById(Integer id);

    Integer add(PlanSubtasks subtask);

    Integer update(Integer id, PlanSubtasks subtask);

    Integer delete(Integer id);
}
