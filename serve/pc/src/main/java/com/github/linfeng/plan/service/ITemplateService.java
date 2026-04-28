package com.github.linfeng.plan.service;

import java.util.List;
import com.github.linfeng.plan.dto.CreateTemplateRequest;
import com.github.linfeng.plan.entity.PlanTemplate;
import com.github.linfeng.plan.entity.PlanWeeks;

/**
 * 计划模板Service接口
 */
public interface ITemplateService {

    List<PlanTemplate> listTemplates();

    List<PlanTemplate> listTemplatesByCategory(String category);

    List<PlanTemplate> searchTemplates(String keyword);

    PlanTemplate getById(Long id);

    Long createTemplate(CreateTemplateRequest request, Integer userId);

    PlanWeeks applyTemplate(Long templateId, java.util.Map<String, String> variables, Integer userId);

    PlanWeeks createFromPlan(Integer planId, String templateName, Boolean isPublic, Integer userId);

    boolean deleteTemplate(Long id, Integer userId);
}
