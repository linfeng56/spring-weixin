package com.github.linfeng.plan.controller;

import jakarta.servlet.http.HttpServletRequest;
import com.github.linfeng.admin.view.message.AjaxViewMessage;
import com.github.linfeng.admin.view.message.MessageType;
import com.github.linfeng.plan.service.IPlanUsersService;
import com.github.linfeng.plan.view.LoginUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 作计划登录
 *
 * @author 黄麟峰
 * @date 2021-03-22
 */
@Controller
@RequestMapping("/plan/login")
public class PlanLoginController {


    /**
     * 用户信息服务
     */
    private final IPlanUsersService planUsersService;

    @Autowired
    public PlanLoginController(IPlanUsersService planUsersService) {
        this.planUsersService = planUsersService;
    }

    /**
     * 登出页.
     *
     * @return 登出页
     */
    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().setAttribute("loginUser", "");
        return "plan/login/login";
    }

    /**
     * 没有权限页面.
     *
     * @return 无权限提示页
     */
    @GetMapping(value = "/unauthorized")
    public String unauthorized() {
        return "plan/login/unauthorized";
    }

    /**
     * 登录页.
     *
     * @return 登录页
     */
    @GetMapping(value = "/index")
    public String index() {
        return "plan/login/login";
    }


    /**
     * 登录处理.
     *
     * @return 成功则进入页, 失败则登录页
     */
    @PostMapping(value = "/index")
    @ResponseBody
    public AjaxViewMessage doLogin(HttpServletRequest request, String loginName, String loginPwd, Integer remember) {
        AjaxViewMessage msg;
        if (!StringUtils.hasText(loginName) || !StringUtils.hasText(loginPwd)) {
            msg = new AjaxViewMessage(MessageType.ERROR, "请求参数不正确", "用户名或密码为空");
            return msg;
        }
        LoginUser loginUser = planUsersService.checkLogin(loginName, loginPwd);
        if (loginUser != null && loginUser.getUserId() > 0) {
            msg = new AjaxViewMessage(MessageType.SUCCESS, "登录成功", "登录成功", "plan/login/into",
                request.getContextPath() + "/plan/weeks/index");
            request.getSession().setAttribute("loginUser", loginUser);
        } else {
            msg = new AjaxViewMessage(MessageType.INFO, "登录失败", "用户名或密码错误", "plan/login/login");
        }
        return msg;
    }

    /**
     * 登录成功跳转页.
     *
     * @return 登录成功跳转页
     */
    @GetMapping(value = "/into")
    public String into(HttpServletRequest request) {
        return "plan/login/into";
    }
}
