import { defHttp } from '/@/utils/http/axios';

export interface BatchImportResult {
  total: number;
  success: number;
  failed: number;
  errors: Array<{
    row: number;
    field: string;
    message: string;
  }>;
}

export const batchApi = {
  downloadTemplate: () => {
    window.open('/api/week-plans/batch/template', '_blank');
  },

  importExcel: (file: File) =>
    defHttp.uploadFile(
      { url: '/api/week-plans/batch/import' },
      { file },
    ),

  batchUpdateStatus: (ids: number[], status: string, remark?: string) =>
    defHttp.put({
      url: '/api/week-plans/batch/status',
      data: { ids, status, remark },
    }),

  batchDelete: (ids: number[]) =>
    defHttp.delete({
      url: '/api/week-plans/batch',
      data: { ids },
    }),
};
