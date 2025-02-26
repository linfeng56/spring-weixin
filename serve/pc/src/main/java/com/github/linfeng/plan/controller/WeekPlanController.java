package com.github.linfeng.plan.controller;

import com.github.linfeng.plan.entity.PlanWeeks;
import com.github.linfeng.plan.service.IPlanWeeksService;
import com.github.linfeng.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/week-plans")
public class WeekPlanController {

    @Autowired
    private IPlanWeeksService weeksService;

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(required = false) Integer status) {
        Integer userId = getCurrentUserId();
        List<PlanWeeks> list;
        if (status != null) {
            list = weeksService.listByUserIdAndStatus(userId, status);
        } else {
            list = weeksService.listByUserId(userId);
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Integer userId = getCurrentUserId();
        PlanWeeks plan = weeksService.getByIdAndUserId(id, userId);
        if (plan == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Plan not found"));
        }
        return ResponseEntity.ok(plan);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> planData) {
        Integer userId = getCurrentUserId();
        PlanWeeks plan = new PlanWeeks();
        plan.setUserId(userId);
        plan.setTitle((String) planData.get("title"));
        plan.setRemarks((String) planData.get("remarks"));
        plan.setBeginDate(((Number) planData.get("beginDate")).longValue());
        plan.setEndDate(((Number) planData.get("endDate")).longValue());
        plan.setCreateDate(DateTimeUtils.DateTimeToLong());
        plan.setStatus(0);

        Integer weekId = weeksService.add(plan);
        return ResponseEntity.ok(Map.of("weekId", weekId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Map<String, Object> planData) {
        Integer userId = getCurrentUserId();
        PlanWeeks existing = weeksService.getByIdAndUserId(id, userId);
        if (existing == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Plan not found"));
        }

        PlanWeeks plan = new PlanWeeks();
        plan.setUserId(userId);
        plan.setTitle((String) planData.get("title"));
        plan.setRemarks((String) planData.get("remarks"));
        plan.setBeginDate(((Number) planData.get("beginDate")).longValue());
        plan.setEndDate(((Number) planData.get("endDate")).longValue());
        if (planData.containsKey("status")) {
            plan.setStatus(((Number) planData.get("status")).intValue());
        } else {
            plan.setStatus(existing.getStatus());
        }

        Integer result = weeksService.update(id, plan);
        if (result > 0) {
            return ResponseEntity.ok(Map.of("message", "Updated successfully"));
        }
        return ResponseEntity.status(500).body(Map.of("error", "Update failed"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Integer userId = getCurrentUserId();
        PlanWeeks existing = weeksService.getByIdAndUserId(id, userId);
        if (existing == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Plan not found"));
        }

        Integer result = weeksService.delete(id, userId);
        if (result > 0) {
            return ResponseEntity.ok(Map.of("message", "Deleted successfully"));
        }
        return ResponseEntity.status(500).body(Map.of("error", "Delete failed"));
    }

    @GetMapping("/week")
    public ResponseEntity<?> getByWeek(@RequestParam Integer year, @RequestParam Integer week) {
        Integer userId = getCurrentUserId();
        List<PlanWeeks> list = weeksService.listByUserId(userId);

        long[] weekRange = getWeekRange(year, week);
        final long start = weekRange[0];
        final long end = weekRange[1];

        List<PlanWeeks> filtered = new java.util.ArrayList<>();
        for (PlanWeeks p : list) {
            if (p.getBeginDate() != null && p.getEndDate() != null) {
                if (p.getBeginDate() <= end && p.getEndDate() >= start) {
                    filtered.add(p);
                }
            }
        }
        return ResponseEntity.ok(filtered);
    }

    private Integer getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() != null) {
            String username = auth.getPrincipal().toString();
            return username.hashCode();
        }
        return 1;
    }

    private long[] getWeekRange(int year, int week) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, 0, 1);
        int firstDayOfYear = cal.get(java.util.Calendar.DAY_OF_WEEK);
        long start = cal.getTimeInMillis() + (week - 1) * 7 * 24L * 3600L * 1000L;
        long end = start + 6 * 24L * 3600L * 1000L;
        return new long[]{start, end};
    }
}
