package com.github.linfeng.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 黄麟峰
 * @date 2021-03-12
 */
@Controller("adminIndexController")
@RequestMapping("/admin")
public class IndexController extends AdminBaseController {

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        checkLogin();

        return "admin/index/index";
    }
}
