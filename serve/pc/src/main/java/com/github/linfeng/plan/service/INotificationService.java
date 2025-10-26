package com.github.linfeng.plan.service;

import com.github.linfeng.plan.entity.PlanReminderSettings;
import com.github.linfeng.plan.entity.UserNotificationSettings;

public interface INotificationService {

    UserNotificationSettings getUserSettings(Integer userId);

    void saveUserSettings(Integer userId, UserNotificationSettings settings);

    PlanReminderSettings getReminderSettings(Integer weekId);

    void saveReminderSettings(Integer weekId, PlanReminderSettings settings);

    void sendReminder(Integer weekId, String type);
}
