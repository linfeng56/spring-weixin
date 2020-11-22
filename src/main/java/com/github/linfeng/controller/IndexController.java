package com.github.linfeng.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.github.linfeng.entity.Users;
import com.github.linfeng.service.IUsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 默认控制器
 *
 * @author 黄麟峰
 */
@Controller
@RequestMapping("/home")
public class IndexController {

    @Autowired
    private IUsersService usersService;

    /**
     * 默认首页
     *
     * @return 首页
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model,
        @RequestParam(value = "id", defaultValue = "0") Integer id) {

        Users user = usersService.getById(id);
        if (null != user) {
            model.addAttribute("name", "用户名:" + user.getUserName());
        } else {
            model.addAttribute("name", "用户不存在!");
        }

        List<Users> users = usersService.list();
        model.addAttribute("users", users);
        return "index";
    }
}
