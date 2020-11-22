package com.github.linfeng.plan.controller;

import javax.servlet.http.HttpServletRequest;
import com.github.linfeng.admin.view.message.MessageType;
import com.github.linfeng.admin.view.message.ViewMessage;
import com.github.linfeng.plan.service.IPlanUsersService;
import com.github.linfeng.plan.view.LoginUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 作计划登录
 *
 * @author 黄麟峰
 * @date 2021-03-22
 */
@Controller
@RequestMapping("/plan/login")
public class PlanLoginController {

    @Autowired
    private IPlanUsersService planUsersService;

    /**
     * 登录页.
     *
     * @return 登录页
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().setAttribute("loginUser", "");
        return "plan/login/into";
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
    @RequestMapping(value = "doLogin", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, String loginName, String loginPwd, Integer remember,
        Model model) {

        ViewMessage msg = null;
        if (!StringUtils.hasText(loginName)
            || !StringUtils.hasText(loginPwd)) {
            msg = new ViewMessage(MessageType.ERROR, "请求参数不正确", "用户名或密码为空");
            model.addAttribute("msg", msg);
            return "plan/login/login";
        }
        if (remember == null) {
            remember = 0;
        }
        LoginUser loginUser = planUsersService.checkLogin(loginName, loginPwd);
        if (loginUser != null && loginUser.getUserId() > 0) {
            msg = new ViewMessage(MessageType.SUCCESS, "登录成功", "登录成功", "plan/login/into",
                request.getContextPath() + "/plan/weeks/index");
            request.getSession().setAttribute("loginUser", loginUser);
        } else {
            msg = new ViewMessage(MessageType.SUCCESS, "登录失败", "用户名或密码错误", "plan/login/login");
        }

        model.addAttribute("remember", remember);
        model.addAttribute("loginName", loginName);
        model.addAttribute("msg", msg);
        return msg.getViewPath();
    }
}
