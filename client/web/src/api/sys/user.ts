import { defHttp } from '/@/utils/http/axios';
import { LoginParams, LoginResultModel, GetUserInfoModel } from './model/userModel';

import { ErrorMessageMode } from '/#/axios';

enum Api {
  Login = '/login',
  Logout = '/logout',
  GetUserInfo = '/getUserInfo',
  GetPermCode = '/getPermCode',
  TestRetry = '/testRetry',
  UserInfo = '/api/user/info',
  UserPassword = '/api/user/password',
  AvatarUpload = '/api/upload/avatar',
}

/**
 * @description: user login api
 */
export function loginApi(params: LoginParams, mode: ErrorMessageMode = 'modal') {
  return defHttp.post<LoginResultModel>(
    {
      url: Api.Login,
      params,
    },
    {
      errorMessageMode: mode,
    },
  );
}

/**
 * @description: getUserInfo
 */
export function getUserInfo() {
  return defHttp.get<GetUserInfoModel>({ url: Api.GetUserInfo }, { errorMessageMode: 'none' });
}

export function getPermCode() {
  return defHttp.get<string[]>({ url: Api.GetPermCode });
}

export function doLogout() {
  return defHttp.get({ url: Api.Logout });
}

export function testRetry() {
  return defHttp.get(
    { url: Api.TestRetry },
    {
      retryRequest: {
        isOpenRetry: true,
        count: 5,
        waitTime: 1000,
      },
    },
  );
}

export interface UserInfoModel {
  uid: number;
  userName: string;
  nickName: string;
  phone: string;
  email: string;
  avatar: string;
  sex: number;
  birthday: number;
  domicile: string;
  nativePlace: string;
  education: string;
  profession: string;
}

export function getUserProfile() {
  return defHttp.get<UserInfoModel>({ url: Api.UserInfo });
}

export function updateUserProfile(data: Partial<UserInfoModel>) {
  return defHttp.put<UserInfoModel>({ url: Api.UserInfo, data });
}

export function changePassword(oldPassword: string, newPassword: string) {
  return defHttp.put(
    { url: Api.UserPassword, data: { oldPassword, newPassword } },
    { errorMessageMode: 'message' },
  );
}

export function uploadAvatar(file: File) {
  const formData = new FormData();
  formData.append('file', file);
  return defHttp.post<{ url: string }>(
    { url: Api.AvatarUpload, data: formData },
    { headers: { 'Content-Type': 'multipart/form-data' } },
  );
}
