package com.github.linfeng.plan.service;

import com.github.linfeng.plan.entity.PlanChangeHistory;

import java.util.List;

public interface IPlanChangeHistoryService {

    List<PlanChangeHistory> listByWeekId(Integer weekId);

    Integer add(PlanChangeHistory history);
}
