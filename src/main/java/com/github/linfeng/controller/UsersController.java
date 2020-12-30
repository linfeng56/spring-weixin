package com.github.linfeng.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户信息 前端控制器.
 *
 * @author 黄麟峰
 * @since 2020-12-30
 */
@Controller
@RequestMapping("/users")
public class UsersController {

    @RequestMapping("/index")
    @ResponseBody
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "index";
    }


    @RequestMapping("/list")
    @ResponseBody
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "list";
    }


    @RequestMapping("/detail/{id}")
    @ResponseBody
    public String list(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
        HttpServletResponse response) {
        return "detail" + id;
    }
}
