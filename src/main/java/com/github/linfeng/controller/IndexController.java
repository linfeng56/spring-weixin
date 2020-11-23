package com.github.linfeng.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 默认控制器
 *
 * @author 黄麟峰
 */
@Controller
@RequestMapping("/home")
public class IndexController {

    /**
     * 默认首页
     *
     * @return 首页
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request,Model model) {
        model.addAttribute("name", "Spring Mvc");
        return "index";
    }
}
