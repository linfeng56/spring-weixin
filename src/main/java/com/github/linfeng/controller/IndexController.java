package com.github.linfeng.controller;

import org.springframework.stereotype.Controller;
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
    public String index() {
        return "index";
    }
}
