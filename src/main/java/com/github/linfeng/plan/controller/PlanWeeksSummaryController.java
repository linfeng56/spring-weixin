package com.github.linfeng.plan.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;
import com.github.linfeng.plan.entity.PlanUsers;
import com.github.linfeng.plan.entity.PlanWeekSummary;
import com.github.linfeng.plan.service.IPlanWeeksSummaryService;
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
@RequestMapping("/plan/summary")
public class PlanWeeksSummaryController extends PlanBaseController {

    @Autowired
    private IPlanWeeksSummaryService weeksSummaryService;


    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        PlanUsers planUser = new PlanUsers();
        if (!checkLogin(planUser)) {
            return "redirect:/plan/login/index";
        }
        model.addAttribute("admin", planUser);
        return "plan/summary/index";
    }


    @RequestMapping("/list")
    public String list(Model model,@RequestParam(value = "searchWeekId", required = false) Integer searchWeekId,
        @RequestParam(value = "table_search", required = false) String searchText,
        HttpServletRequest request, HttpServletResponse response) {
        PlanUsers planUser = new PlanUsers();
        if (!checkLogin(planUser)) {
            return "redirect:/plan/login/index";
        }
        model.addAttribute("admin", planUser);

        List<PlanWeekSummary> list;
        if (StringUtils.hasText(searchText)) {
            list = weeksSummaryService.list(searchText);
        } else {
            list = weeksSummaryService.list();
        }
        model.addAttribute("weeks", list);

        return "plan/summary/list";
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model, HttpServletRequest request, HttpServletResponse response) {
        PlanUsers planUser = new PlanUsers();
        if (!checkLogin(planUser)) {
            return "redirect:/plan/login/index";
        }
        model.addAttribute("admin", planUser);
        return "plan/summary/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    public String doAdd(Model model, String weekSummary, Integer weekId, Integer userId, String remarks) {
        PlanUsers planUser = new PlanUsers();
        if (!checkLogin(planUser)) {
            return "redirect:/plan/login/index";
        }
        model.addAttribute("admin", planUser);

        List<String> errorMessage = new ArrayList<>(5);
        if (!StringUtils.hasText(weekSummary)) {
            errorMessage.add("总结不能为空!");
        }
        if (weekId==null || weekId<1) {
            errorMessage.add("请选择对应周计划!");
        }
        if (userId==null || userId<1) {
            errorMessage.add("请选择对应用户!");
        }
        if (!errorMessage.isEmpty()) {
            return "plan/summary/add";
        }
        if (!StringUtils.hasText(remarks)) {
            remarks = "";
        }

        PlanWeekSummary summary = new PlanWeekSummary();
        summary.setSummary(weekSummary);
        summary.setWeekId(weekId);
        summary.setUserId(userId);
        summary.setCreateDate(DateTimeUtils.DateTimeToLong());
        summary.setRemarks(remarks);

        Integer summaryId = weeksSummaryService.add(summary);
        model.addAttribute("summaryId", summaryId);

        return "redirect:list";
    }


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Integer id, Model model) {
        PlanUsers planUser = new PlanUsers();
        if (!checkLogin(planUser)) {
            return "redirect:/plan/login/index";
        }
        model.addAttribute("admin", planUser);
        PlanWeekSummary item;
        if (id > 0) {
            item = weeksSummaryService.getById(id);
        } else {
            item = new PlanWeekSummary();
            item.setId(-1);
        }

        model.addAttribute("item", item);
        return "plan/summary/edit";
    }

    @RequestMapping(value = "/doEdit/{id}", method = RequestMethod.POST)
    public String doEdit(@PathVariable("id") Integer id, Model model, String weekSummary, Integer weekId, Integer userId, String remarks) {
        PlanUsers planUser = new PlanUsers();
        if (!checkLogin(planUser)) {
            return "redirect:/plan/login/index";
        }
        model.addAttribute("admin", planUser);
        model.addAttribute("id", id);

        List<String> errorMessage = new ArrayList<>(5);
        if (!StringUtils.hasText(weekSummary)) {
            errorMessage.add("总结不能为空!");
        }
        if (weekId==null || weekId<1) {
            errorMessage.add("请选择对应周计划!");
        }
        if (userId==null || userId<1) {
            errorMessage.add("请选择对应用户!");
        }
        if (!errorMessage.isEmpty()) {
            return "plan/summary/add";
        }
        if (!StringUtils.hasText(remarks)) {
            remarks = "";
        }

        PlanWeekSummary summary = new PlanWeekSummary();
        summary.setSummary(weekSummary);
        summary.setWeekId(weekId);
        summary.setUserId(userId);
        summary.setCreateDate(DateTimeUtils.DateTimeToLong());
        summary.setRemarks(remarks);

        Integer updateRows = weeksSummaryService.update(id, summary);
        model.addAttribute("updateRows", updateRows);

        return "redirect:/plan/summary/list";
    }

    @RequestMapping("/detail/{id}")
    @ResponseBody
    public String list(@PathVariable("id") Integer id) {
        Map<String, Object> ret = new HashMap<>(6);
        if (null == id || id <= 0) {
            ret.put("retCode", "fail");
            ret.put("errMsg", "error id");
            return JSONObject.toJSONString(ret);
        }
        PlanWeekSummary info = weeksSummaryService.getById(id);
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
