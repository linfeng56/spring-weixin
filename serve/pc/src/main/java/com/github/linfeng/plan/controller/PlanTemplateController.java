package com.github.linfeng.plan.controller;

import com.github.linfeng.plan.dto.ApplyTemplateRequest;
import com.github.linfeng.plan.dto.CreateTemplateRequest;
import com.github.linfeng.plan.entity.PlanTemplate;
import com.github.linfeng.plan.entity.PlanWeeks;
import com.github.linfeng.plan.service.ITemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 计划模板Controller
 */
@RestController
@RequestMapping("/api/templates")
public class PlanTemplateController {

    @Autowired
    private ITemplateService templateService;

    @GetMapping
    public ResponseEntity<?> list(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword) {
        List<PlanTemplate> list;
        if (keyword != null && !keyword.isEmpty()) {
            list = templateService.searchTemplates(keyword);
        } else if (category != null && !category.isEmpty()) {
            list = templateService.listTemplatesByCategory(category);
        } else {
            list = templateService.listTemplates();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        PlanTemplate template = templateService.getById(id);
        if (template == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(template);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateTemplateRequest request) {
        Integer userId = getCurrentUserId();
        Long templateId = templateService.createTemplate(request, userId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("id", templateId);
        result.put("message", "模板创建成功");
        
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/apply")
    public ResponseEntity<?> applyTemplate(
            @PathVariable Long id,
            @RequestBody(required = false) ApplyTemplateRequest request) {
        Integer userId = getCurrentUserId();
        
        Map<String, String> variables = request != null && request.getVariables() != null
            ? request.getVariables()
            : new HashMap<>();
        
        PlanWeeks plan = templateService.applyTemplate(id, variables, userId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("planId", plan.getWeekId());
        result.put("title", plan.getTitle());
        result.put("message", "模板应用成功，已创建新计划");
        
        return ResponseEntity.ok(result);
    }

    @PostMapping("/from-plan/{planId}")
    public ResponseEntity<?> createFromPlan(
            @PathVariable Integer planId,
            @RequestParam String templateName,
            @RequestParam(required = false, defaultValue = "false") Boolean isPublic) {
        Integer userId = getCurrentUserId();
        templateService.createFromPlan(planId, templateName, isPublic, userId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("message", "模板保存成功");
        
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Integer userId = getCurrentUserId();
        boolean success = templateService.deleteTemplate(id, userId);
        
        if (success) {
            Map<String, Object> result = new HashMap<>();
            result.put("message", "模板删除成功");
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "删除失败"));
        }
    }

    private Integer getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getDetails() instanceof Integer) {
            return (Integer) auth.getDetails();
        }
        return 1;
    }
}
