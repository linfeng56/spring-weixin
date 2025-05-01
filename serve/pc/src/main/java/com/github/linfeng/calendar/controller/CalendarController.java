package com.github.linfeng.calendar.controller;

import com.github.linfeng.plan.entity.PlanWeeks;
import com.github.linfeng.plan.service.IPlanWeeksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    @Autowired
    private IPlanWeeksService weeksService;

    @GetMapping("/events")
    public ResponseEntity<?> getEvents() {
        Integer userId = getCurrentUserId();
        List<PlanWeeks> plans = weeksService.listByUserId(userId);

        List<Map<String, Object>> events = new ArrayList<>();
        for (PlanWeeks plan : plans) {
            if (plan.getBeginDate() != null && plan.getEndDate() != null) {
                Map<String, Object> event = new HashMap<>();
                event.put("id", plan.getWeekId());
                event.put("title", plan.getTitle());
                event.put("start", plan.getBeginDate());
                event.put("end", plan.getEndDate() + 24 * 3600 * 1000);
                event.put("status", plan.getStatus());
                event.put("color", getStatusColor(plan.getStatus()));
                events.add(event);
            }
        }

        return ResponseEntity.ok(events);
    }

    private Integer getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() != null) {
            return auth.getPrincipal().toString().hashCode();
        }
        return 1;
    }

    private String getStatusColor(Integer status) {
        if (status == null) return "#1890ff";
        switch (status) {
            case 0: return "#faad14";
            case 1: return "#1890ff";
            case 2: return "#52c41a";
            case 3: return "#8c8c8c";
            default: return "#1890ff";
        }
    }
}
