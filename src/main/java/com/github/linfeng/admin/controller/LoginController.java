package com.github.linfeng.admin.controller;

import javax.servlet.http.HttpServletRequest;
import com.github.linfeng.admin.service.LoginService;
import com.github.linfeng.admin.view.message.MessageType;
import com.github.linfeng.admin.view.message.ViewMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 登录.
 *
 * @author 黄麟峰
 * @date 2021-03-12
 */
@Controller
@RequestMapping("/admin/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录页.
     *
     * @return 登录页
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {

        return "admin/login/login";
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
        if (StringUtils.isEmpty(loginName)
            || StringUtils.isEmpty(loginPwd)) {
            msg = new ViewMessage(MessageType.ERROR, "请求参数不正确", "用户名或密码为空");
            model.addAttribute("msg", msg);
            return "admin/login/login";
        }
        if (remember == null) {
            remember = 0;
        }

        if (loginService.checkLogin(loginName, loginPwd)) {
            msg = new ViewMessage(MessageType.SUCCESS, "登录成功", "登录成功", "admin/login/into",
                request.getContextPath() + "/admin/index");
            request.getSession().setAttribute("admin", loginName);
        } else {
            msg = new ViewMessage(MessageType.SUCCESS, "登录失败", "用户名或密码错误", "admin/login/login");
        }

        model.addAttribute("remember", remember);
        model.addAttribute("loginName", loginName);
        model.addAttribute("msg", msg);
        return msg.getViewPath();
    }

}
