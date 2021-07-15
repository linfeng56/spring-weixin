package com.github.linfeng.plan.controller;

import javax.servlet.http.HttpServletRequest;
import com.github.linfeng.plan.entity.PlanUsers;

import org.springframework.util.ObjectUtils;
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
     * @param planUser 用户空对象
     * @return true已登录，false未登录。
     */
    protected boolean checkLogin(PlanUsers planUser) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            .getRequest();
        Object user = request.getSession().getAttribute("planUser");
        if (!ObjectUtils.isEmpty(user)) {
            planUser.setUserName(user.toString());
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查用户是否登录
     *
     * @return true已登录，false未登录。
     */
    protected boolean checkLogin() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            .getRequest();
        Object user = request.getSession().getAttribute("planUser");
        if (!ObjectUtils.isEmpty(user)) {
            return true;
        } else {
            return false;
        }
    }
}
