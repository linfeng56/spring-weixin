package com.github.linfeng.plan.controller;


import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import com.github.linfeng.plan.entity.PlanUsers;
import com.github.linfeng.plan.entity.User;
import com.github.linfeng.plan.service.IPlanUsersService;
import com.github.linfeng.plan.service.IUserService;
import com.github.linfeng.plan.view.ResponseView;
import com.github.linfeng.utils.DateTimeUtils;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 前端控制器.
 *
 * @author 黄麟峰
 */
@Controller
@RequestMapping("/plan/users")
public class PlanUsersController extends BasePlanController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlanUsersController.class);
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

    @RequiresRoles("normaladmin")
    @RequestMapping("/add")
    public String add(Model model, HttpServletRequest request, HttpServletResponse response) {

        return "plan/users/add";
    }

    @RequiresRoles("normaladmin")
    @RequestMapping("/doAdd")
    @ResponseBody
    public ResponseView<Long> doAdd(String username, String name, String password, String rePassword,
        Integer locked) {
        locked = locked == null ? 0 : locked;
        ResponseView<Long> errorMessage = validForm(username, name, password, rePassword, locked);
        if (errorMessage != null) {
            return errorMessage;
        }

        User user = new User(username, password);
        user.setName(name);
        user.setLocked(locked == 1);
        user.setCreateDate(DateTimeUtils.DateTimeToLong());
        String rnd = ((Long) new Random().nextLong()).toString();
        user.setSalt(Base64.encodeBase64URLSafeString(rnd.getBytes(StandardCharsets.UTF_8)));
        String encodePassword = new SimpleHash("MD5", password, user.getCredentialsSalt(), 2).toHex();
        user.setPassword(encodePassword);
        Long userId = userService.createUser(user);
        LOGGER.info("新增加用户:" + user.toString());
        return new ResponseView<>(200, "操作成功", userId);
    }

    @RequiresRoles("normaladmin")
    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value = "table_search", required = false) String searchText,
        HttpServletRequest request, HttpServletResponse response) {
        List<User> list;
        if (StringUtils.hasText(searchText)) {
            list = userService.list(searchText);
        } else {
            list = userService.allUsers();
        }
        model.addAttribute("users", list);
        // TODO:增加权限的展示
        return "plan/users/list";
    }

    @RequiresRoles("normaladmin")
    @RequestMapping("/cnt")
    @ResponseBody
    public ResponseView<Integer> cnt() {
        Integer cnt = userService.cnt();
        if (cnt == null) {
            cnt = 0;
        }
        return new ResponseView<>(200, "OK", cnt);
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

    @RequestMapping("/del/{id}")
    @ResponseBody
    public Object del(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
        HttpServletResponse response) {
        Map<String, Object> ret = new HashMap<>(6);
        if (null == id || id <= 0) {
            ret.put("status", "-1");
            ret.put("errMsg", "用户编号不正确");
            return ret;
        }
        boolean up = userService.del(id);
        if (up) {
            ret.put("status", "200");
            ret.put("errMsg", "success");
        } else {
            ret.put("status", "-2");
            ret.put("errMsg", "删除用户失败[" + id + "]");
        }
        return ret;
    }

    /**
     * 验证表单数据
     *
     * @param weekTitle 标题
     * @param weekBegin 开始日期
     * @param weekEnd   结束日期
     * @return 异常提示对象或null
     */
    /**
     * @param username   登录名
     * @param name       姓名
     * @param password   密码
     * @param rePassword 重复密码
     * @param locked     是否锁定
     * @return 异常提示对象或null
     */
    private ResponseView<Long> validForm(String username, String name, String password, String rePassword,
        Integer locked) {
        StringBuilder errorMessage = new StringBuilder(25);
        if (!StringUtils.hasText(username)) {
            errorMessage.append("登录名不能为空!").append("\\n");
        }
        if (!StringUtils.hasText(name)) {
            errorMessage.append("姓名不能为空!").append("\\n");
        }
        if (!StringUtils.hasText(password)) {
            errorMessage.append("密码不能为空!").append("\\n");
        } else if (!password.equals(rePassword)) {
            errorMessage.append("两次输入的密码不致!").append("\\n");
        }
        if (StringUtils.hasText(errorMessage)) {
            return new ResponseView<>(401, errorMessage.toString());
        }
        return null;
    }
}
