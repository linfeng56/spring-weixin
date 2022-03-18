package com.github.linfeng.plan.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * 工作计划控制器基类
 *
 * @author 黄麟峰
 * @date 2021-03-22
 */
public abstract class BasePlanController {

    /**
     * 是否为ajax请求
     *
     * @param request request
     * @return true为ajax请求，否则为false.
     */
    protected boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"));
    }
}
