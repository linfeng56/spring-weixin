import { defHttp } from '/@/utils/http/axios';

export interface SysRole {
  roleId?: number;
  roleName: string;
  roleKey: string;
  description?: string;
  status: number;
  createTime?: number;
}

export interface SysPermission {
  permId?: number;
  permName: string;
  permKey: string;
  parentId: number;
  sort: number;
  status: number;
}

export function getRoles() {
  return defHttp.get<SysRole[]>({ url: '/api/admin/roles' });
}

export function getRoleById(id: number) {
  return defHttp.get<SysRole>({ url: `/api/admin/roles/${id}` });
}

export function createRole(data: SysRole) {
  return defHttp.post({ url: '/api/admin/roles', data });
}

export function updateRole(id: number, data: SysRole) {
  return defHttp.put({ url: `/api/admin/roles/${id}`, data });
}

export function deleteRole(id: number) {
  return defHttp.delete({ url: `/api/admin/roles/${id}` });
}

export function getPermissions() {
  return defHttp.get<SysPermission[]>({ url: '/api/admin/permissions' });
}

export function getRolePermissions(roleId: number) {
  return defHttp.get<SysPermission[]>({ url: `/api/admin/roles/${roleId}/permissions` });
}
