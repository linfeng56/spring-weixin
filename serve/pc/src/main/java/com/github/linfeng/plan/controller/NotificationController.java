package com.github.linfeng.plan.controller;

import com.github.linfeng.plan.entity.PlanReminderSettings;
import com.github.linfeng.plan.entity.UserNotificationSettings;
import com.github.linfeng.plan.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private INotificationService notificationService;

    @GetMapping("/user/settings")
    public ResponseEntity<?> getUserSettings() {
        Integer userId = getCurrentUserId();
        UserNotificationSettings settings = notificationService.getUserSettings(userId);
        return ResponseEntity.ok(settings);
    }

    @PutMapping("/user/settings")
    public ResponseEntity<?> saveUserSettings(@RequestBody UserNotificationSettings settings) {
        Integer userId = getCurrentUserId();
        notificationService.saveUserSettings(userId, settings);
        return ResponseEntity.ok(Collections.singletonMap("message", "Settings saved successfully"));
    }

    @GetMapping("/plan/{weekId}/settings")
    public ResponseEntity<?> getPlanSettings(@PathVariable Integer weekId) {
        PlanReminderSettings settings = notificationService.getReminderSettings(weekId);
        return ResponseEntity.ok(settings);
    }

    @PutMapping("/plan/{weekId}/settings")
    public ResponseEntity<?> savePlanSettings(@PathVariable Integer weekId, @RequestBody PlanReminderSettings settings) {
        notificationService.saveReminderSettings(weekId, settings);
        return ResponseEntity.ok(Collections.singletonMap("message", "Settings saved successfully"));
    }

    private Integer getCurrentUserId() {
        return 1;
    }
}
