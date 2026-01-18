package com.github.linfeng.app.service;

import com.github.linfeng.app.entity.PlanProgress;
import com.github.linfeng.app.entity.WeekPlan;
import com.github.linfeng.app.enums.PlanStatus;
import com.github.linfeng.app.repository.PlanProgressRepository;
import com.github.linfeng.app.repository.WeekPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProgressService {

    @Autowired
    private WeekPlanRepository weekPlanRepository;

    @Autowired
    private PlanProgressRepository planProgressRepository;

    @Transactional
    public void updateProgress(Long planId, Integer progress, String remark, Long userId) {
        if (progress < 0 || progress > 100) {
            throw new IllegalArgumentException("进度值必须在0-100之间");
        }

        WeekPlan plan = weekPlanRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("计划不存在"));

        if (!plan.getUserId().equals(userId)) {
            throw new IllegalArgumentException("无权限操作此计划");
        }

        if (plan.getStatus() == PlanStatus.ARCHIVED) {
            throw new IllegalArgumentException("已归档的计划不允许更新进度");
        }

        plan.setCurrentProgress(progress);
        if (progress == 100 && plan.getStatus() != PlanStatus.COMPLETED) {
            plan.setStatus(PlanStatus.COMPLETED);
        }
        weekPlanRepository.save(plan);

        PlanProgress progressRecord = new PlanProgress();
        progressRecord.setPlanId(planId);
        progressRecord.setProgress(progress);
        progressRecord.setRemark(remark);
        progressRecord.setCreatedBy(userId);
        planProgressRepository.save(progressRecord);
    }

    public List<PlanProgress> getProgressHistory(Long planId) {
        return planProgressRepository.findByPlanIdOrderByCreatedAtDesc(planId);
    }

    public Map<Long, Integer> getBatchProgress(List<Long> planIds) {
        List<WeekPlan> plans = weekPlanRepository.findAllById(planIds);
        Map<Long, Integer> result = new HashMap<>();
        for (WeekPlan plan : plans) {
            result.put(plan.getId(), plan.getCurrentProgress());
        }
        return result;
    }
}
