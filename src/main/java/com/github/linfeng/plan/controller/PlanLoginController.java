package com.github.linfeng.plan.controller;

import javax.servlet.http.HttpServletRequest;
import com.github.linfeng.admin.view.message.AjaxViewMessage;
import com.github.linfeng.admin.view.message.MessageType;
import com.github.linfeng.plan.service.IPlanUsersService;
import com.github.linfeng.plan.view.LoginUser;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().setAttribute("loginUser", "");
        return "plan/login/login";
    }

    /**
     * 登录页.
     *
     * @return 登录页
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {

        return "plan/login/login";
    }


    /**
     * 登录处理.
     *
     * @return 成功则进入页, 失败则登录页
     */
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @ResponseBody
    public AjaxViewMessage doLogin(HttpServletRequest request, String loginName, String loginPwd, Integer remember) {

        AjaxViewMessage msg = null;
        if (!StringUtils.hasText(loginName)
            || !StringUtils.hasText(loginPwd)) {
            msg = new AjaxViewMessage(MessageType.ERROR, "请求参数不正确", "用户名或密码为空");
            return msg;
        }
        if (remember == null) {
            remember = 0;
        }
        LoginUser loginUser = planUsersService.checkLogin(loginName, loginPwd);
        if (loginUser != null && loginUser.getUserId() > 0) {

            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(loginName, loginPwd);
            try {
                // 登录
                subject.login(token);
            } catch (AuthenticationException e) {
                if (e instanceof UnknownAccountException) {
                    msg = new AjaxViewMessage(MessageType.INFO, "登录失败", "用户不存在.shiro.", "plan/login/login");
                } else {
                    msg = new AjaxViewMessage(MessageType.INFO, "登录失败", "用户名或密码错误.shiro.", "plan/login/login");
                }
            }
            // 验证登录
            if (subject.isAuthenticated()) {
                msg = new AjaxViewMessage(MessageType.SUCCESS, "登录成功", "登录成功.shiro.", "plan/login/into",
                    request.getContextPath() + "/plan/weeks/index");
                request.getSession().setAttribute("loginUser", loginUser);
            }
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
    @RequestMapping(value = "/into", method = RequestMethod.GET)
    public String into(HttpServletRequest request) {
        return "plan/login/into";
    }
}
