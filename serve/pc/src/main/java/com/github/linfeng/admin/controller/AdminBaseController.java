package com.github.linfeng.admin.controller;

import javax.servlet.http.HttpServletRequest;
import com.github.linfeng.admin.view.AdminUser;

import org.springframework.util.ObjectUtils;
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

    /**
     * 检查用户是否登录
     *
     * @param adminUser 管理员空对象
     * @return true已登录，false未登录。
     */
    protected boolean checkLogin(AdminUser adminUser) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Object admin = request.getSession().getAttribute("admin");
        if (!ObjectUtils.isEmpty(admin)) {
            adminUser.setUserName(admin.toString());
            return true;
        } else {
            return false;
        }
    }
}
