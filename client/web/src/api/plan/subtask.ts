export interface PlanSubtask {
  subtaskId?: number;
  weekId: number;
  userId?: number;
  title: string;
  description?: string;
  status: number;
  progress: number;
  createDate?: number;
  updateDate?: number;
}

export function getSubtasks(weekId: number) {
  return defHttp.get<PlanSubtask[]>({ url: `/api/week-plans/${weekId}/subtasks` });
}

export function createSubtask(weekId: number, data: Partial<PlanSubtask>) {
  return defHttp.post({ url: `/api/week-plans/${weekId}/subtasks`, data });
}

export function updateSubtask(weekId: number, subtaskId: number, data: Partial<PlanSubtask>) {
  return defHttp.put({ url: `/api/week-plans/${weekId}/subtasks/${subtaskId}`, data });
}

export function deleteSubtask(weekId: number, subtaskId: number) {
  return defHttp.delete({ url: `/api/week-plans/${weekId}/subtasks/${subtaskId}` });
}
