package com.github.linfeng.plan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.linfeng.plan.dto.CreateTemplateRequest;
import com.github.linfeng.plan.entity.PlanTemplate;
import com.github.linfeng.plan.entity.PlanWeeks;
import com.github.linfeng.plan.mapper.PlanTemplateMapper;
import com.github.linfeng.plan.mapper.PlanWeeksMapper;
import com.github.linfeng.plan.service.ITemplateService;
import com.github.linfeng.utils.DateTimeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 计划模板Service实现类
 */
@Service
public class TemplateServiceImpl implements ITemplateService {

    @Autowired
    private PlanTemplateMapper templateMapper;

    @Autowired
    private PlanWeeksMapper planWeeksMapper;

    @Override
    public List<PlanTemplate> listTemplates() {
        return templateMapper.list();
    }

    @Override
    public List<PlanTemplate> listTemplatesByCategory(String category) {
        return templateMapper.listByCategory(category);
    }

    @Override
    public List<PlanTemplate> searchTemplates(String keyword) {
        return templateMapper.searchTemplates(keyword);
    }

    @Override
    public PlanTemplate getById(Long id) {
        return templateMapper.getById(id);
    }

    @Override
    @Transactional
    public Long createTemplate(CreateTemplateRequest request, Integer userId) {
        PlanTemplate template = new PlanTemplate();
        template.setName(request.getName());
        template.setDescription(request.getDescription());
        template.setCategory(request.getCategory());
        template.setContent(request.getContent());
        template.setIsSystem(false);
        template.setIsPublic(request.getIsPublic() != null ? request.getIsPublic() : false);
        template.setUserId(userId);
        template.setUseCount(0);
        
        return templateMapper.add(template);
    }

    @Override
    @Transactional
    public PlanWeeks applyTemplate(Long templateId, Map<String, String> variables, Integer userId) {
        PlanTemplate template = templateMapper.getById(templateId);
        if (template == null) {
            throw new RuntimeException("模板不存在");
        }

        templateMapper.incrementUseCount(templateId);

        String content = replaceVariables(template.getContent(), variables);
        
        PlanWeeks plan = new PlanWeeks();
        plan.setUserId(userId);
        plan.setTitle(extractTitle(content));
        plan.setRemarks(content);
        plan.setBeginDate(System.currentTimeMillis());
        plan.setEndDate(System.currentTimeMillis() + 7 * 24L * 3600L * 1000L);
        plan.setCreateDate(DateTimeUtils.DateTimeToLong());
        plan.setStatus(0);

        Integer planId = planWeeksMapper.add(plan);
        plan.setWeekId(planId);
        
        return plan;
    }

    @Override
    @Transactional
    public PlanWeeks createFromPlan(Integer planId, String templateName, Boolean isPublic, Integer userId) {
        PlanWeeks existingPlan = planWeeksMapper.getByIdAndUserId(planId, userId);
        if (existingPlan == null) {
            throw new RuntimeException("计划不存在");
        }

        PlanTemplate template = new PlanTemplate();
        template.setName(templateName);
        template.setDescription("从计划创建");
        template.setCategory("WORK");
        template.setContent(existingPlan.getRemarks());
        template.setIsSystem(false);
        template.setIsPublic(isPublic != null ? isPublic : false);
        template.setUserId(userId);
        template.setUseCount(0);

        templateMapper.add(template);

        return existingPlan;
    }

    @Override
    @Transactional
    public boolean deleteTemplate(Long id, Integer userId) {
        PlanTemplate template = templateMapper.getById(id);
        if (template == null) {
            throw new RuntimeException("模板不存在");
        }

        if (template.getIsSystem()) {
            throw new RuntimeException("系统模板不能删除");
        }

        if (!template.getUserId().equals(userId)) {
            throw new RuntimeException("无权限删除此模板");
        }

        return templateMapper.deleteById(id) > 0;
    }

    private String replaceVariables(String text, Map<String, String> variables) {
        if (variables == null || variables.isEmpty()) {
            return text;
        }

        Pattern pattern = Pattern.compile("\\{\\{(\\w+)\\}\\}");
        Matcher matcher = pattern.matcher(text);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String varName = matcher.group(1);
            String replacement = variables.getOrDefault(varName, matcher.group(0));
            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(result);

        return result.toString();
    }

    private String extractTitle(String content) {
        try {
            Map<String, Object> map = new HashMap<>();
            return (String) map.get("title");
        } catch (Exception e) {
            return "新计划";
        }
    }
}
