package com.github.linfeng.app.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.linfeng.app.dto.ApiResponse;
import com.github.linfeng.app.dto.StatusChangeRequest;
import com.github.linfeng.app.entity.WeekPlan;
import com.github.linfeng.app.enums.PlanStatus;
import com.github.linfeng.app.repository.WeekPlanRepository;
import com.github.linfeng.app.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/week-plans")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @Autowired
    private WeekPlanRepository weekPlanRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PutMapping("/{id}/status")
    public ApiResponse<Void> changeStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusChangeRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId) {
        try {
            PlanStatus targetStatus = PlanStatus.valueOf(request.getStatus());
            statusService.changeStatus(id, targetStatus, request.getRemark(), userId);
            return ApiResponse.success(null);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @GetMapping("/{id}/status-history")
    public ApiResponse<List<JsonNode>> getStatusHistory(@PathVariable Long id) {
        try {
            WeekPlan plan = weekPlanRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("计划不存在"));

            String historyJson = plan.getStatusHistory();
            if (historyJson == null || historyJson.isEmpty()) {
                return ApiResponse.success(new ArrayList<>());
            }

            JsonNode history = objectMapper.readTree(historyJson);
            List<JsonNode> result = new ArrayList<>();
            history.forEach(result::add);
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}
