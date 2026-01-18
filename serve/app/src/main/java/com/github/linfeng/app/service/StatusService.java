package com.github.linfeng.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.linfeng.app.entity.WeekPlan;
import com.github.linfeng.app.enums.PlanStatus;
import com.github.linfeng.app.repository.WeekPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class StatusService {

    @Autowired
    private WeekPlanRepository weekPlanRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional
    public void changeStatus(Long planId, PlanStatus targetStatus, String remark, Long userId) {
        WeekPlan plan = weekPlanRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("计划不存在"));

        if (!plan.getUserId().equals(userId)) {
            throw new IllegalArgumentException("无权限操作此计划");
        }

        PlanStatus currentStatus = plan.getStatus();
        if (!currentStatus.canTransitionTo(targetStatus)) {
            throw new IllegalArgumentException(
                String.format("不允许从%s流转到%s", currentStatus.getLabel(), targetStatus.getLabel())
            );
        }

        plan.setStatus(targetStatus);
        addStatusHistory(plan, targetStatus, userId, remark);
        weekPlanRepository.save(plan);
    }

    private void addStatusHistory(WeekPlan plan, PlanStatus status, Long userId, String remark) {
        try {
            ArrayNode history;
            String historyJson = plan.getStatusHistory();
            if (historyJson == null || historyJson.isEmpty()) {
                history = objectMapper.createArrayNode();
            } else {
                history = (ArrayNode) objectMapper.readTree(historyJson);
            }

            ObjectNode record = objectMapper.createObjectNode();
            record.put("status", status.name());
            record.put("statusLabel", status.getLabel());
            record.put("timestamp", LocalDateTime.now().format(formatter));
            record.put("operatorId", userId);
            if (remark != null) {
                record.put("remark", remark);
            }

            history.add(record);
            plan.setStatusHistory(objectMapper.writeValueAsString(history));
        } catch (Exception e) {
            throw new RuntimeException("状态历史记录失败", e);
        }
    }
}
