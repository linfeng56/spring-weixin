package com.github.linfeng.plan.controller;

import com.github.linfeng.plan.entity.PlanWeeks;
import com.github.linfeng.plan.service.IPlanWeeksService;
import com.github.linfeng.plan.service.WeekPlanExportService;
import com.github.linfeng.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/week-plans")
public class WeekPlanController {

    @Autowired
    private IPlanWeeksService weeksService;

    @Autowired
    private WeekPlanExportService exportService;

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

    @PutMapping("/{id}/summary")
    public ResponseEntity<?> updateSummary(@PathVariable Integer id, @RequestBody Map<String, Object> summaryData) {
        Integer userId = getCurrentUserId();
        PlanWeeks existing = weeksService.getByIdAndUserId(id, userId);
        if (existing == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Plan not found"));
        }

        String summary = (String) summaryData.get("summary");
        Long summaryDate = DateTimeUtils.DateTimeToLong();

        Integer result = weeksService.updateSummary(id, summary, summaryDate);
        if (result > 0) {
            return ResponseEntity.ok(Map.of("message", "Summary updated successfully"));
        }
        return ResponseEntity.status(500).body(Map.of("error", "Update failed"));
    }

    @GetMapping("/export")
    public ResponseEntity<?> export(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(defaultValue = "word") String format) {
        try {
            Integer userId = getCurrentUserId();
            List<PlanWeeks> plans = weeksService.listByUserId(userId);

            if (year != null && month != null) {
                final long[] monthRange = getMonthRange(year, month);
                final long start = monthRange[0];
                final long end = monthRange[1];
                plans = plans.stream()
                    .filter(p -> p.getBeginDate() != null && p.getEndDate() != null)
                    .filter(p -> p.getBeginDate() <= end && p.getEndDate() >= start)
                    .collect(java.util.stream.Collectors.toList());
            }

            byte[] documentBytes = exportService.exportToWord(plans, format);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "week-plan-export.docx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(documentBytes);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Export failed: " + e.getMessage()));
        }
    }

    private long[] getMonthRange(int year, int month) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setLenient(false);
        cal.set(year, month - 1, 1, 0, 0, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        long start = cal.getTimeInMillis();
        cal.set(java.util.Calendar.DAY_OF_MONTH, cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH));
        cal.set(java.util.Calendar.HOUR_OF_DAY, 23);
        cal.set(java.util.Calendar.MINUTE, 59);
        cal.set(java.util.Calendar.SECOND, 59);
        cal.set(java.util.Calendar.MILLISECOND, 999);
        long end = cal.getTimeInMillis();
        return new long[]{start, end};
    }

    @GetMapping("/summary")
    public ResponseEntity<?> getSummary(@RequestParam(required = false, defaultValue = "week") String type) {
        Integer userId = getCurrentUserId();
        List<PlanWeeks> plans = weeksService.listByUserId(userId);

        Map<String, Object> summary = new HashMap<>();
        summary.put("total", plans.size());

        long now = System.currentTimeMillis();
        long weekAgo = now - 7 * 24L * 3600L * 1000L;
        long monthAgo = now - 30 * 24L * 3600L * 1000L;

        long thisWeek = plans.stream()
            .filter(p -> p.getBeginDate() != null && p.getBeginDate() >= weekAgo)
            .count();
        long thisMonth = plans.stream()
            .filter(p -> p.getBeginDate() != null && p.getBeginDate() >= monthAgo)
            .count();

        summary.put("thisWeek", thisWeek);
        summary.put("thisMonth", thisMonth);

        long completed = plans.stream()
            .filter(p -> p.getStatus() != null && p.getStatus() == 2)
            .count();
        long inProgress = plans.stream()
            .filter(p -> p.getStatus() != null && p.getStatus() == 1)
            .count();
        long pending = plans.stream()
            .filter(p -> p.getStatus() != null && p.getStatus() == 0)
            .count();

        Map<String, Long> statusCount = new HashMap<>();
        statusCount.put("pending", pending);
        statusCount.put("inProgress", inProgress);
        statusCount.put("completed", completed);
        summary.put("statusCount", statusCount);

        Map<String, Long> byMonth = new HashMap<>();
        java.text.SimpleDateFormat monthFormat = new java.text.SimpleDateFormat("yyyy-MM");
        for (PlanWeeks plan : plans) {
            if (plan.getBeginDate() != null) {
                String month = monthFormat.format(new java.util.Date(plan.getBeginDate()));
                byMonth.put(month, byMonth.getOrDefault(month, 0L) + 1);
            }
        }
        summary.put("byMonth", byMonth);

        return ResponseEntity.ok(summary);
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
