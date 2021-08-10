package com.github.linfeng.plan.controller;

import javax.servlet.http.HttpServletRequest;
import com.github.linfeng.plan.view.LoginUser;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 工作计划控制器基类
 *
 * @author 黄麟峰
 * @date 2021-03-22
 */
public abstract class PlanBaseController {

    /**
     * 检查用户是否登录
     *
     * @param loginUser 用户空对象
     * @return true已登录，false未登录。
     */
    protected boolean checkLogin(LoginUser loginUser) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            .getRequest();
        Object user = request.getSession().getAttribute("loginUser");
        if (user != null && user instanceof LoginUser) {
            loginUser = (LoginUser) user;
            if (loginUser != null && loginUser.getUserId() > 0) {
                return true;
            }
        }
        return false;

    }

    /**
     * 检查用户是否登录
     *
     * @return true已登录，false未登录。
     */
    protected boolean checkLogin() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            .getRequest();
        Object user = request.getSession().getAttribute("loginUser");
        if (user != null && user instanceof LoginUser) {
            LoginUser loginUser = (LoginUser) user;
            if (loginUser != null && loginUser.getUserId() > 0) {
                return true;
            }
        }
        return false;
    }
}
