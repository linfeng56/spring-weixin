package com.github.linfeng.controller;


import com.alibaba.fastjson.JSONObject;
import com.github.linfeng.entity.Users;
import com.github.linfeng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息 前端控制器.
 *
 * @author 黄麟峰
 * @since 2020-12-30
 */
@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    @ResponseBody
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "index";
    }


    @RequestMapping("/list")
    @ResponseBody
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> ret = new HashMap<>(6);
        List<Users> list = userService.list();
        if (null != list) {
            ret.put("retCode", "success");
            ret.put("errMsg", "success");
            ret.put("data", list);

        } else {
            ret.put("retCode", "fail");
            ret.put("errMsg", "list is null");
        }
        return JSONObject.toJSONString(ret);
    }


    @RequestMapping("/detail/{id}")
    @ResponseBody
    public String list(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
                       HttpServletResponse response) {
        Map<String, Object> ret = new HashMap<>(6);
        if (null == id || id <= 0) {
            ret.put("retCode", "fail");
            ret.put("errMsg", "error id");
            return JSONObject.toJSONString(ret);
        }
        Users users = userService.getById(id);
        if (null != users) {
            ret.put("retCode", "success");
            ret.put("errMsg", "success");
            ret.put("data", users);
        } else {
            ret.put("retCode", "fail");
            ret.put("errMsg", "not exists id:[" + id + "]");
        }
        return JSONObject.toJSONString(ret);
    }
}
