package com.github.linfeng.plan.task;

import com.github.linfeng.plan.entity.PlanReminderSettings;
import com.github.linfeng.plan.entity.PlanWeeks;
import com.github.linfeng.plan.mapper.PlanReminderSettingsMapper;
import com.github.linfeng.plan.service.INotificationService;
import com.github.linfeng.plan.service.IPlanWeeksService;
import com.github.linfeng.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReminderScheduledTask {

    @Autowired
    private IPlanWeeksService weeksService;

    @Autowired
    private PlanReminderSettingsMapper reminderSettingsMapper;

    @Autowired
    private INotificationService notificationService;

    @Scheduled(cron = "0 0 9 * * ?")
    public void checkAndSendReminders() {
        long now = System.currentTimeMillis();
        long oneDay = 24L * 3600 * 1000;
        long oneDayLater = now + oneDay;
        long twoDaysLater = now + 2 * oneDay;

        List<PlanWeeks> plans = weeksService.list();
        for (PlanWeeks plan : plans) {
            if (plan.getStatus() != null && plan.getStatus() == 2) continue;
            if (plan.getEndDate() == null) continue;

            PlanReminderSettings settings = reminderSettingsMapper.getByWeekId(plan.getWeekId());
            if (settings == null) continue;

            long endDate = plan.getEndDate();
            if (endDate >= now && endDate < oneDayLater) {
                if (settings.getRemindOnDay() == 1 && settings.getRemindedOnDay() == 0) {
                    notificationService.sendReminder(plan.getWeekId(), "remind_on_day");
                    settings.setRemindedOnDay(1);
                    reminderSettingsMapper.update(settings);
                }
            } else if (endDate >= oneDayLater && endDate < twoDaysLater) {
                if (settings.getRemindBefore1day() == 1 && settings.getRemindedBefore1day() == 0) {
                    notificationService.sendReminder(plan.getWeekId(), "remind_before_1day");
                    settings.setRemindedBefore1day(1);
                    reminderSettingsMapper.update(settings);
                }
            }
        }
    }
}
