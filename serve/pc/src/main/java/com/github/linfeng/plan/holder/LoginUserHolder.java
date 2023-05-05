package com.github.linfeng.plan.holder;

import com.github.linfeng.plan.view.LoginUser;

/**
 * 登录用户信息保持
 *
 * @author 黄麟峰
 * @date 2021-08-10
 */
public class LoginUserHolder {

    private static final ThreadLocal<LoginUser> LOGIN_USER = new ThreadLocal<>();

    private LoginUserHolder(){}
    /**
     * 设置登录用户
     *
     * @param user 登录用户信息
     */
    public static void setLoginUser(LoginUser user) {
        LOGIN_USER.set(user);
    }

    /**
     * 读取登录用户
     *
     * @return 当前登录用户或null
     */
    public static LoginUser getLoginUser() {
        return LOGIN_USER.get();
    }

    /**
     * 清理
     */
    public static void remove() {
        LOGIN_USER.remove();
    }
}
