import { defHttp } from '/@/utils/http/axios';

export interface UserNotificationSettings {
  userId?: number;
  email: string;
  dingtalkWebhook: string;
  enableEmail: number;
  enableDingtalk: number;
}

export interface PlanReminderSettings {
  settingId?: number;
  weekId: number;
  userId?: number;
  remindBefore1day: number;
  remindOnDay: number;
}

export function getUserNotificationSettings() {
  return defHttp.get<UserNotificationSettings>({ url: '/api/notification/user/settings' });
}

export function saveUserNotificationSettings(data: UserNotificationSettings) {
  return defHttp.put({ url: '/api/notification/user/settings', data });
}

export function getPlanReminderSettings(weekId: number) {
  return defHttp.get<PlanReminderSettings>({ url: `/api/notification/plan/${weekId}/settings` });
}

export function savePlanReminderSettings(weekId: number, data: PlanReminderSettings) {
  return defHttp.put({ url: `/api/notification/plan/${weekId}/settings`, data });
}
