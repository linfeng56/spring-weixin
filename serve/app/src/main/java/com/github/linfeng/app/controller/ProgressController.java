package com.github.linfeng.app.controller;

import com.github.linfeng.app.dto.ApiResponse;
import com.github.linfeng.app.dto.ProgressHistoryResponse;
import com.github.linfeng.app.dto.ProgressUpdateRequest;
import com.github.linfeng.app.entity.PlanProgress;
import com.github.linfeng.app.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/week-plans")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @PostMapping("/{id}/progress")
    public ApiResponse<Void> updateProgress(
            @PathVariable Long id,
            @Valid @RequestBody ProgressUpdateRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId) {
        try {
            progressService.updateProgress(id, request.getProgress(), request.getRemark(), userId);
            return ApiResponse.success(null);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @GetMapping("/{id}/progress")
    public ApiResponse<List<ProgressHistoryResponse>> getProgressHistory(@PathVariable Long id) {
        List<PlanProgress> history = progressService.getProgressHistory(id);
        List<ProgressHistoryResponse> response = history.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success(response);
    }

    @GetMapping("/progress")
    public ApiResponse<Map<Long, Integer>> getBatchProgress(@RequestParam String ids) {
        List<Long> planIds = parseIds(ids);
        Map<Long, Integer> progress = progressService.getBatchProgress(planIds);
        return ApiResponse.success(progress);
    }

    private ProgressHistoryResponse convertToResponse(PlanProgress progress) {
        ProgressHistoryResponse response = new ProgressHistoryResponse();
        response.setId(progress.getId());
        response.setProgress(progress.getProgress());
        response.setRemark(progress.getRemark());
        response.setCreatedBy(progress.getCreatedBy());
        response.setCreatedAt(progress.getCreatedAt());
        return response;
    }

    private List<Long> parseIds(String ids) {
        String[] idArray = ids.split(",");
        return java.util.Arrays.stream(idArray)
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}
