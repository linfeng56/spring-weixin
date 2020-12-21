package com.github.linfeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.linfeng.model.User;
import com.github.linfeng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认控制器
 *
 * @author 黄麟峰
 */
@Controller
@RequestMapping("/mini/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 默认列表
     *
     * @return 列表
     */
    @RequestMapping(value = "/list", produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String list(HttpServletRequest request, Model model) {
        String params = request.getParameter("p");


        List<User> users = userService.getAllUsers();
        Map<String, Object> result = new HashMap<>(6);

        result.put("status", "success");
        result.put("userList", users);
        String retData = JSONObject.toJSONString(result);
        return retData;
    }
}
