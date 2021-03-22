package com.github.linfeng.plan.controller;

import org.springframework.stereotype.Controller;
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

    /**
     * 登录页.
     *
     * @return 登录页
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {

        return "plan/login/login";
    }
}
