package com.github.linfeng.admin.controller;

import com.github.linfeng.admin.view.AdminUser;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 黄麟峰
 * @date 2021-03-12
 */
@Controller("adminIndexController")
@RequestMapping("/admin")
public class IndexController extends AdminBaseController {

    @RequestMapping("/index")
    public String index(Model model) {
        AdminUser adminUser = new AdminUser();
        if (!checkLogin(adminUser)) {
            return "redirect:/admin/login/index";
        }
        model.addAttribute("admin", adminUser);

        return "admin/index/index";
    }
}
