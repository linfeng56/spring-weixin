package com.github.linfeng.plan.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;
import com.github.linfeng.plan.entity.PlanUsers;
import com.github.linfeng.plan.entity.PlanWeeks;
import com.github.linfeng.plan.service.IPlanWeeksService;
import com.github.linfeng.utils.DateTimeUtils;

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
@RequestMapping("/plan/weeks")
public class PlanWeeksController extends PlanBaseController {

    @Autowired
    private IPlanWeeksService weeksService;


    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        PlanUsers planUser = new PlanUsers();
        if (!checkLogin(planUser)) {
            return "redirect:/plan/login/index";
        }
        model.addAttribute("admin", planUser);
        return "plan/weeks/index";
    }


    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value = "table_search", required = false) String searchText,
        HttpServletRequest request, HttpServletResponse response) {
        PlanUsers planUser = new PlanUsers();
        if (!checkLogin(planUser)) {
            return "redirect:/plan/login/index";
        }
        model.addAttribute("admin", planUser);

        List<PlanWeeks> list;
        if (StringUtils.hasText(searchText)) {
            list = weeksService.list(searchText);
        } else {
            list = weeksService.list();
        }
        model.addAttribute("weeks", list);

        return "plan/weeks/list";
    }


    @RequestMapping("/add")
    public String add(Model model, HttpServletRequest request, HttpServletResponse response) {
        PlanUsers planUser = new PlanUsers();
        if (!checkLogin(planUser)) {
            return "redirect:/plan/login/index";
        }
        model.addAttribute("admin", planUser);
        return "plan/weeks/add";
    }

    @RequestMapping("/doAdd")
    public String doAdd(Model model, String weekTitle, String weekBegin, String weekEnd, String remarks) {
        PlanUsers planUser = new PlanUsers();
        if (!checkLogin(planUser)) {
            return "redirect:/plan/login/index";
        }
        model.addAttribute("admin", planUser);

        List<String> errorMessage = new ArrayList<>(5);
        if (!StringUtils.hasText(weekTitle)) {
            errorMessage.add("标题不能为空!");
        }
        if (!StringUtils.hasText(weekBegin)) {
            errorMessage.add("请选择开始日期!");
        }
        if (!StringUtils.hasText(weekEnd)) {
            errorMessage.add("请选择截止日期!");
        }
        if (!errorMessage.isEmpty()) {
            return "plan/weeks/add";
        }
        if (!StringUtils.hasText(remarks)) {
            remarks = "";
        }

        PlanWeeks weeks = new PlanWeeks();
        weeks.setTitle(weekTitle);
        Long beginEpoch = DateTimeUtils.DateToLong(weekBegin);
        Long endEpoch = DateTimeUtils.DateToLong(weekEnd);

        weeks.setBeginDate(beginEpoch);
        weeks.setEndDate(endEpoch);
        weeks.setCreateDate(DateTimeUtils.DateTimeToLong());
        weeks.setRemarks(remarks);

        Integer weekId = weeksService.add(weeks);
        model.addAttribute("weekId", weekId);

        return "redirect:list";
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
        PlanWeeks info = weeksService.getById(id);
        if (null != info) {
            ret.put("retCode", "success");
            ret.put("errMsg", "success");
            ret.put("data", info);
        } else {
            ret.put("retCode", "fail");
            ret.put("errMsg", "not exists id:[" + id + "]");
        }
        return JSONObject.toJSONString(ret);
    }
}
