import { defHttp } from '/@/utils/http/axios';

export interface PlanTemplate {
  id?: number;
  name: string;
  description?: string;
  category: string;
  content: string;
  isSystem?: boolean;
  isPublic?: boolean;
  userId?: number;
  useCount?: number;
  createdAt?: number;
  updatedAt?: number;
}

export interface CreateTemplateParams {
  name: string;
  description?: string;
  category: string;
  content: string;
  isPublic?: boolean;
}

export interface ApplyTemplateParams {
  variables?: Record<string, string>;
}

export function getTemplates(params?: { category?: string; keyword?: string }) {
  return defHttp.get<PlanTemplate[]>({ url: '/api/templates', params });
}

export function getTemplateById(id: number) {
  return defHttp.get<PlanTemplate>({ url: `/api/templates/${id}` });
}

export function createTemplate(data: CreateTemplateParams) {
  return defHttp.post<{ id: number; message: string }>({ url: '/api/templates', data });
}

export function applyTemplate(id: number, params?: ApplyTemplateParams) {
  return defHttp.post<{ planId: number; title: string; message: string }>({
    url: `/api/templates/${id}/apply`,
    data: params || {},
  });
}

export function createTemplateFromPlan(
  planId: number,
  templateName: string,
  isPublic: boolean = false
) {
  return defHttp.post<{ message: string }>({
    url: `/api/templates/from-plan/${planId}`,
    params: { templateName, isPublic },
  });
}

export function deleteTemplate(id: number) {
  return defHttp.delete<{ message: string }>({ url: `/api/templates/${id}` });
}

export const TemplateCategory = {
  WORK: 'WORK',
  STUDY: 'STUDY',
  LIFE: 'LIFE',
  OTHER: 'OTHER',
} as const;

export const TemplateCategoryText: Record<string, string> = {
  WORK: '工作',
  STUDY: '学习',
  LIFE: '生活',
  OTHER: '其他',
};
