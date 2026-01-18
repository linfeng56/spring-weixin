import { defHttp } from '/@/utils/http/axios';

export interface StatusChangeRequest {
  status: string;
  remark?: string;
}

export interface StatusHistoryItem {
  status: string;
  statusLabel: string;
  timestamp: string;
  operatorId: number;
  remark?: string;
}

export const statusApi = {
  changeStatus: (planId: number, data: StatusChangeRequest) =>
    defHttp.put({ url: `/api/week-plans/${planId}/status`, data }),

  getStatusHistory: (planId: number) =>
    defHttp.get<StatusHistoryItem[]>({ url: `/api/week-plans/${planId}/status-history` }),
};
