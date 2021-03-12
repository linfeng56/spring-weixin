package com.github.linfeng.admin.service;

/**
 * 后台登录服务
 *
 * @author 黄麟峰
 * @date 2021-03-12
 */
public interface LoginService {

    /**
     * 判断登录名和密码是否匹配
     *
     * @param userName 登录名
     * @param password 密码
     * @return true匹配, false不匹配
     */
    boolean checkLogin(String userName, String password);
}
