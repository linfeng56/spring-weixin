import { defHttp } from '/@/utils/http/axios';

export interface PlanWeek {
  weekId?: number;
  userId?: number;
  title: string;
  remarks?: string;
  beginDate: number;
  endDate: number;
  createDate?: number;
  summary?: string;
  summaryDate?: number;
  status?: number;
}

export enum PlanStatus {
  PENDING = 0,
  IN_PROGRESS = 1,
  COMPLETED = 2,
  ARCHIVED = 3,
}

export const PlanStatusText: Record<number, string> = {
  [PlanStatus.PENDING]: '待提交',
  [PlanStatus.IN_PROGRESS]: '进行中',
  [PlanStatus.COMPLETED]: '已完成',
  [PlanStatus.ARCHIVED]: '已归档',
};

export function getWeekPlans(status?: number) {
  const params = status !== undefined ? { status } : {};
  return defHttp.get<PlanWeek[]>({ url: '/api/week-plans', params });
}

export function getWeekPlanById(id: number) {
  return defHttp.get<PlanWeek>({ url: `/api/week-plans/${id}` });
}

export function createWeekPlan(data: PlanWeek) {
  return defHttp.post({ url: '/api/week-plans', data });
}

export function updateWeekPlan(id: number, data: PlanWeek) {
  return defHttp.put({ url: `/api/week-plans/${id}`, data });
}

export function deleteWeekPlan(id: number) {
  return defHttp.delete({ url: `/api/week-plans/${id}` });
}

export function getWeekPlansByWeek(year: number, week: number) {
  return defHttp.get<PlanWeek[]>({ url: '/api/week-plans/week', params: { year, week } });
}

export function updateWeekPlanSummary(id: number, summary: string) {
  return defHttp.put({ url: `/api/week-plans/${id}/summary`, data: { summary } });
}

export function exportWeekPlans(params: { year?: number; month?: number; format?: string }) {
  return defHttp.get<string>({ url: '/api/week-plans/export', params, responseType: 'blob' });
}

export interface PlanSummary {
  total: number;
  thisWeek: number;
  thisMonth: number;
  statusCount: {
    pending: number;
    inProgress: number;
    completed: number;
  };
  byMonth: Record<string, number>;
}

export function getPlanSummary(type?: string) {
  return defHttp.get<PlanSummary>({ url: '/api/week-plans/summary', params: { type } });
}
