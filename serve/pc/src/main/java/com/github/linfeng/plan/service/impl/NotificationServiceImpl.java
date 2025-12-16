package com.github.linfeng.plan.service.impl;

import com.github.linfeng.plan.entity.PlanReminderSettings;
import com.github.linfeng.plan.entity.PlanWeeks;
import com.github.linfeng.plan.entity.UserNotificationSettings;
import com.github.linfeng.plan.mapper.PlanReminderSettingsMapper;
import com.github.linfeng.plan.mapper.UserNotificationSettingsMapper;
import com.github.linfeng.plan.service.INotificationService;
import com.github.linfeng.plan.service.IPlanWeeksService;
import com.github.linfeng.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationServiceImpl implements INotificationService {

    @Autowired
    private UserNotificationSettingsMapper userSettingsMapper;

    @Autowired
    private PlanReminderSettingsMapper reminderSettingsMapper;

    @Autowired
    private IPlanWeeksService weeksService;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public UserNotificationSettings getUserSettings(Integer userId) {
        UserNotificationSettings settings = userSettingsMapper.getByUserId(userId);
        if (settings == null) {
            settings = new UserNotificationSettings();
            settings.setUserId(userId);
            settings.setEnableEmail(1);
            settings.setEnableDingtalk(1);
        }
        return settings;
    }

    @Override
    public void saveUserSettings(Integer userId, UserNotificationSettings settings) {
        settings.setUserId(userId);
        settings.setUpdateTime(DateTimeUtils.DateTimeToLong());
        UserNotificationSettings existing = userSettingsMapper.getByUserId(userId);
        if (existing == null) {
            settings.setCreateTime(DateTimeUtils.DateTimeToLong());
            userSettingsMapper.insert(settings);
        } else {
            userSettingsMapper.update(settings);
        }
    }

    @Override
    public PlanReminderSettings getReminderSettings(Integer weekId) {
        PlanReminderSettings settings = reminderSettingsMapper.getByWeekId(weekId);
        if (settings == null) {
            settings = new PlanReminderSettings();
            settings.setWeekId(weekId);
            settings.setRemindBefore1day(1);
            settings.setRemindOnDay(1);
        }
        return settings;
    }

    @Override
    public void saveReminderSettings(Integer weekId, PlanReminderSettings settings) {
        settings.setWeekId(weekId);
        settings.setUpdateTime(DateTimeUtils.DateTimeToLong());
        PlanReminderSettings existing = reminderSettingsMapper.getByWeekId(weekId);
        if (existing == null) {
            settings.setCreateTime(DateTimeUtils.DateTimeToLong());
            reminderSettingsMapper.insert(settings);
        } else {
            reminderSettingsMapper.update(settings);
        }
    }

    @Override
    public void sendReminder(Integer weekId, String type) {
        PlanWeeks plan = weeksService.getById(weekId);
        if (plan == null) return;

        UserNotificationSettings userSettings = getUserSettings(plan.getUserId());
        String content = buildReminderContent(plan, type);

        if (userSettings.getEnableEmail() == 1 && userSettings.getEmail() != null) {
            sendEmail(userSettings.getEmail(), "周计划提醒 - " + plan.getTitle(), content);
        }

        if (userSettings.getEnableDingtalk() == 1 && userSettings.getDingtalkWebhook() != null) {
            sendDingtalk(userSettings.getDingtalkWebhook(), content);
        }
    }

    private String buildReminderContent(PlanWeeks plan, String type) {
        StringBuilder sb = new StringBuilder();
        sb.append("【周计划提醒】\n\n");
        sb.append("计划标题: ").append(plan.getTitle()).append("\n");
        sb.append("提醒类型: ").append("截止前1天".equals(type) ? "截止前一天提醒" : "当天提醒").append("\n");
        if (plan.getBeginDate() != null) {
            sb.append("开始日期: ").append(formatDate(plan.getBeginDate())).append("\n");
        }
        if (plan.getEndDate() != null) {
            sb.append("结束日期: ").append(formatDate(plan.getEndDate())).append("\n");
        }
        return sb.toString();
    }

    private void sendEmail(String to, String subject, String content) {
        System.out.println("[Email] To: " + to + ", Subject: " + subject + ", Content: " + content);
    }

    private void sendDingtalk(String webhook, String content) {
        try {
            Map<String, Object> request = new HashMap<>();
            request.put("msgtype", "text");
            Map<String, Object> text = new HashMap<>();
            text.put("content", content);
            request.put("text", text);
            restTemplate.postForObject(webhook, request, String.class);
        } catch (Exception e) {
            System.err.println("[Dingtalk] Send failed: " + e.getMessage());
        }
    }

    private String formatDate(Long timestamp) {
        if (timestamp == null) return "";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new java.util.Date(timestamp));
    }
}
