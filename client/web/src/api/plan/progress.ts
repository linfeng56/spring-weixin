import { defHttp } from '/@/utils/http/axios';

export interface ProgressUpdateRequest {
  progress: number;
  remark?: string;
}

export interface ProgressHistoryItem {
  id: number;
  progress: number;
  remark?: string;
  createdBy: number;
  createdAt: string;
}

export const progressApi = {
  updateProgress: (planId: number, data: ProgressUpdateRequest) =>
    defHttp.post({ url: `/api/week-plans/${planId}/progress`, data }),

  getProgressHistory: (planId: number) =>
    defHttp.get<ProgressHistoryItem[]>({ url: `/api/week-plans/${planId}/progress` }),

  getBatchProgress: (planIds: number[]) =>
    defHttp.get<Record<number, number>>({
      url: `/api/week-plans/progress`,
      params: { ids: planIds.join(',') },
    }),
};
