package com.github.linfeng.plan.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;
import com.github.linfeng.plan.entity.PlanUsers;
import com.github.linfeng.plan.entity.PlanWeeks;
import com.github.linfeng.plan.service.IPlanWeeksService;
import com.github.linfeng.plan.view.ResponseView;
import com.github.linfeng.utils.DateTimeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model, HttpServletRequest request, HttpServletResponse response) {
        PlanUsers planUser = new PlanUsers();
        if (!checkLogin(planUser)) {
            return "redirect:/plan/login/index";
        }
        model.addAttribute("admin", planUser);
        return "plan/weeks/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public ResponseView<Integer> doAdd(String weekTitle, String weekBegin, String weekEnd,
        String remarks) {
        PlanUsers planUser = new PlanUsers();
        if (!checkLogin(planUser)) {
            return new ResponseView<>(301, "请登录后再操作");
        }

        ResponseView<Integer> errorMessage = validForm(weekTitle, weekBegin, weekEnd);
        if (errorMessage != null) {
            return errorMessage;
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

        return new ResponseView<>(200, "操作成功", weekId);
    }

    private ResponseView<Integer> validForm(String weekTitle, String weekBegin, String weekEnd) {
        StringBuffer errorMessage = new StringBuffer(5);
        if (!StringUtils.hasText(weekTitle)) {
            errorMessage.append("标题不能为空!").append("\\n");
        }
        if (!StringUtils.hasText(weekBegin)) {
            errorMessage.append("请选择开始日期!").append("\\n");
        }
        if (!StringUtils.hasText(weekEnd)) {
            errorMessage.append("请选择截止日期!").append("\\n");
        }
        if (StringUtils.hasText(errorMessage)) {
            return new ResponseView<>(401, errorMessage.toString());
        }
        return null;
    }


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Integer id, Model model) {
        PlanUsers planUser = new PlanUsers();
        if (!checkLogin(planUser)) {
            return "redirect:/plan/login/index";
        }
        model.addAttribute("admin", planUser);
        PlanWeeks item;
        if (id > 0) {
            item = weeksService.getById(id);
        } else {
            item = new PlanWeeks();
            item.setWeekId(-1);
        }

        model.addAttribute("item", item);
        return "plan/weeks/edit";
    }

    @RequestMapping(value = "/doEdit/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseView<Integer> doEdit(@PathVariable("id") Integer id, Model model, String weekTitle, String weekBegin,
        String weekEnd, String remarks) {
        PlanUsers planUser = new PlanUsers();
        if (!checkLogin(planUser)) {
            return new ResponseView<>(301, "请登录后再操作");
        }

        ResponseView<Integer> errorMessage = validForm(weekTitle, weekBegin, weekEnd);
        if (errorMessage != null) {
            return errorMessage;
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
        weeks.setRemarks(remarks);

        Integer updateRows = weeksService.update(id, weeks);
        if (updateRows > 0) {
            return new ResponseView<>(200, "操作成功", id);
        } else {
            return new ResponseView<>(500, "操作失败", id);
        }
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


    @RequestMapping("/summary/{id}")
    @ResponseBody
    public String summary(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
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
