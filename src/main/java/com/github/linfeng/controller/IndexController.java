package com.github.linfeng.controller;

import com.github.linfeng.model.User;
import com.github.linfeng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 默认控制器
 *
 * @author 黄麟峰
 */
@Controller
@RequestMapping("/home")
public class IndexController {

    @Autowired
    private UserService userService;

    /**
     * 默认首页
     *
     * @return 首页
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request, Model model) {
        String userIdString = request.getParameter("id");

        Integer userId = null;
        if (null != userIdString) {
            userId = Integer.parseInt(userIdString);
        }
        if (null == userId) {
            userId = 1;
        }

        User user = userService.getUser(userId);
        if (null != user) {
            model.addAttribute("name", "用户名:" + user.getUserName());
        } else {
            model.addAttribute("name", "用户不存在!");
        }

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "index";
    }
}
