package com.github.linfeng.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 管理后台控制器基类
 *
 * @author 黄麟峰
 * @date 2021-03-12
 */
public abstract class AdminBaseController {

    protected boolean checkLogin() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Object admin = request.getSession().getAttribute("admin");
        if (!StringUtils.isEmpty(admin)) {
            return true;
        } else {

            return false;
        }
    }
}
