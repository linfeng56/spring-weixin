package com.github.linfeng.plan.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import com.github.linfeng.plan.entity.PlanUsers;
import com.github.linfeng.plan.entity.User;
import com.github.linfeng.plan.holder.LoginUserHolder;
import com.github.linfeng.plan.service.IPlanUsersService;
import com.github.linfeng.plan.service.IUserService;
import com.github.linfeng.plan.view.LoginUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 前端控制器.
 *
 * @author 黄麟峰
 */
@Controller
@RequestMapping("/plan/users")
public class PlanUsersController extends BasePlanController {

    /**
     * 用户服务
     */
    private final IPlanUsersService service;
    private final IUserService userService;

    @Autowired
    public PlanUsersController(IPlanUsersService service, IUserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @RequestMapping("/index")
    @ResponseBody
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "index";
    }


    @RequestMapping("/list-rb")
    @ResponseBody
    public String listRb(Model model, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> ret = new HashMap<>(6);
        List<PlanUsers> list = service.list();
        if (null != list) {
            ret.put("retCode", "success");
            ret.put("errMsg", "success");
            ret.put("data", list);

        } else {
            ret.put("retCode", "fail");
            ret.put("errMsg", "list is null");
        }
        return JSON.toJSONString(ret);
    }

    @RequestMapping("/list")
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {
        LoginUser loginUser = LoginUserHolder.getLoginUser();
        model.addAttribute("admin", loginUser);
        List<User> list = userService.allUsers();
        model.addAttribute("users", list);
        // TODO:增加权限的展示
        return "plan/users/list";
    }


    @RequestMapping("/detail/{id}")
    @ResponseBody
    public String list(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
        HttpServletResponse response) {
        Map<String, Object> ret = new HashMap<>(6);
        if (null == id || id <= 0) {
            ret.put("retCode", "fail");
            ret.put("errMsg", "error id");
            return JSON.toJSONString(ret);
        }
        PlanUsers info = service.getById(id);
        if (null != info) {
            ret.put("retCode", "success");
            ret.put("errMsg", "success");
            ret.put("data", info);
        } else {
            ret.put("retCode", "fail");
            ret.put("errMsg", "not exists id:[" + id + "]");
        }
        return JSON.toJSONString(ret);
    }


    @RequestMapping("/lock/{id}")
    @ResponseBody
    public Object lock(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
        HttpServletResponse response) {
        Map<String, Object> ret = new HashMap<>(6);
        if (null == id || id <= 0) {
            ret.put("status", "-1");
            ret.put("errMsg", "用户编号不正确");
            return ret;
        }
        boolean up = userService.lock(id);
        if (up) {
            ret.put("status", "200");
            ret.put("errMsg", "success");
            User user = userService.findUser(id);
            ret.put("data", user);
            ret.put("locked", user.getLocked() ? 1 : 0);
        } else {
            ret.put("status", "-2");
            ret.put("errMsg", "锁定用户失败[" + id + "]");
        }
        return ret;
    }

    @RequestMapping("/unlock/{id}")
    @ResponseBody
    public Object unlock(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
        HttpServletResponse response) {
        Map<String, Object> ret = new HashMap<>(6);
        if (null == id || id <= 0) {
            ret.put("status", "-1");
            ret.put("errMsg", "用户编号不正确");
            return ret;
        }
        boolean up = userService.unlock(id);
        if (up) {
            ret.put("status", "200");
            ret.put("errMsg", "success");
            User user = userService.findUser(id);
            ret.put("data", user);
            ret.put("locked", user.getLocked() ? 1 : 0);
        } else {
            ret.put("status", "-2");
            ret.put("errMsg", "开启用户失败[" + id + "]");
        }
        return ret;
    }
}
