package com.github.linfeng.plan.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;
import com.github.linfeng.plan.entity.PlanItems;
import com.github.linfeng.plan.entity.PlanUsers;
import com.github.linfeng.plan.service.IPlanItemsService;
import com.github.linfeng.plan.service.IPlanUsersService;
import com.github.linfeng.plan.service.IPlanWeeksService;
import com.github.linfeng.plan.view.JobType;
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
 * 周计划任务项控制器.
 *
 * @author 黄麟峰
 */
@Controller
@RequestMapping("/plan/items")
public class PlanItemsController extends PlanBaseController {

    @Autowired
    private IPlanItemsService itemsService;

    @Autowired
    private IPlanUsersService usersService;

    @Autowired
    private IPlanWeeksService weeksService;

    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        PlanUsers planUser = new PlanUsers();
        if (!checkLogin(planUser)) {
            return "redirect:/plan/login/index";
        }
        model.addAttribute("admin", planUser);
        return "plan/items/index";
    }


    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value = "searchWeekId", required = false) Integer searchWeekId,
        @RequestParam(value = "searchText", required = false) String searchText,
        HttpServletRequest request, HttpServletResponse response) {
        PlanUsers planUser = new PlanUsers();
        if (!checkLogin(planUser)) {
            return "redirect:/plan/login/index";
        }
        model.addAttribute("admin", planUser);

        List<PlanItems> list = null;
        if (searchWeekId == null || searchWeekId < 1) {
            list = itemsService.list();
        } else {
            list = itemsService.listByWeek(searchWeekId, searchText);
        }

        model.addAttribute("items", list);
        model.addAttribute("types", JobType.allMap());
        model.addAttribute("weeks", weeksService.list());
        model.addAttribute("searchWeekId", searchWeekId != null ? searchWeekId : 0);

        return "plan/items/list";
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model, HttpServletRequest request, HttpServletResponse response) {
        PlanUsers planUser = new PlanUsers();
        if (!checkLogin(planUser)) {
            return "redirect:/plan/login/index";
        }
        model.addAttribute("admin", planUser);

        Map jobTypes = JobType.toMap();
        model.addAttribute("jobTypes", jobTypes);

        List<PlanUsers> users = usersService.list();
        model.addAttribute("users", users);
        model.addAttribute("weeks", weeksService.list());
        return "plan/items/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    public String doAdd(Model model, Integer itemJobType, Integer itemJobNum, String itemTitle, Integer itemUserId,
        Integer itemWeekId, String itemBegin, String itemEnd, String itemFinish, String content, String remarks) {
        PlanUsers planUser = new PlanUsers();
        if (!checkLogin(planUser)) {
            return "redirect:/plan/login/index";
        }
        model.addAttribute("admin", planUser);

        List<String> errorMessage = new ArrayList<>(5);
        if (!StringUtils.hasText(itemTitle)) {
            errorMessage.add("标题不能为空!");
        }
        if (!StringUtils.hasText(itemBegin)) {
            errorMessage.add("请选择开始日期!");
        }
        if (!StringUtils.hasText(itemEnd)) {
            errorMessage.add("请选择截止日期!");
        }
        if (itemWeekId == null || itemWeekId < 1) {
            errorMessage.add("请选择所属周计划!");
        }
        if (!errorMessage.isEmpty()) {
            return "plan/items/add";
        }
        if (!StringUtils.hasText(remarks)) {
            remarks = "";
        }

        PlanItems item = new PlanItems();
        item.setJobType(itemJobType);
        item.setJobNum(itemJobNum);
        item.setTitle(itemTitle);
        item.setUserId(itemUserId);
        item.setWeekId(itemWeekId);
        Long beginEpoch = DateTimeUtils.DateToLong(itemBegin);
        Long endEpoch = DateTimeUtils.DateToLong(itemEnd);
        Long finishEpoch = DateTimeUtils.DateToLong(itemFinish);

        item.setBeginDate(beginEpoch);
        item.setEndDate(endEpoch);
        item.setJobFinishDate(finishEpoch);

        item.setContent(content);
        item.setRemarks(remarks);
        item.setCreateDate(DateTimeUtils.DateTimeToLong());
        item.setEditDate(DateTimeUtils.DateTimeToLong());

        Integer itemId = itemsService.add(item);
        model.addAttribute("itemsId", itemId);

        return "redirect:list";
    }


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
        HttpServletResponse response) {
        PlanUsers planUser = new PlanUsers();
        if (!checkLogin(planUser)) {
            return "redirect:/plan/login/index";
        }
        model.addAttribute("admin", planUser);
        PlanItems item;
        if (id > 0) {
            item = itemsService.getById(id);
        } else {
            item = new PlanItems();
            item.setId(-1);
        }

        Map<String, String> jobTypes = JobType.toMap();
        model.addAttribute("jobTypes", jobTypes);

        List<PlanUsers> users = usersService.list();
        model.addAttribute("users", users);
        model.addAttribute("weeks", weeksService.list());
        model.addAttribute("item", item);
        return "plan/items/edit";
    }

    @RequestMapping(value = "/doEdit/{id}", method = RequestMethod.POST)
    public String doEdit(@PathVariable("id") Integer id, Model model, Integer itemJobType, Integer itemJobNum,
        String itemTitle, Integer itemUserId, Integer itemWeekId, String itemBegin, String itemEnd,
        String itemFinish, String content, String remarks) {
        PlanUsers planUser = new PlanUsers();
        if (!checkLogin(planUser)) {
            return "redirect:/plan/login/index";
        }
        model.addAttribute("admin", planUser);
        model.addAttribute("itemsId", id);

        List<String> errorMessage = new ArrayList<>(5);
        if (!StringUtils.hasText(itemTitle)) {
            errorMessage.add("标题不能为空!");
        }
        if (!StringUtils.hasText(itemBegin)) {
            errorMessage.add("请选择开始日期!");
        }
        if (!StringUtils.hasText(itemEnd)) {
            errorMessage.add("请选择截止日期!");
        }
        if (itemWeekId == null || itemWeekId < 1) {
            errorMessage.add("请选择所属周计划!");
        }
        if (!errorMessage.isEmpty()) {
            return "plan/items/edit";
        }
        if (!StringUtils.hasText(remarks)) {
            remarks = "";
        }

        PlanItems item = new PlanItems();
        item.setJobType(itemJobType);
        item.setJobNum(itemJobNum);
        item.setTitle(itemTitle);
        item.setUserId(itemUserId);
        item.setWeekId(itemWeekId);
        Long beginEpoch = DateTimeUtils.DateToLong(itemBegin);
        Long endEpoch = DateTimeUtils.DateToLong(itemEnd);
        Long finishEpoch = DateTimeUtils.DateToLong(itemFinish);

        item.setBeginDate(beginEpoch);
        item.setEndDate(endEpoch);
        item.setJobFinishDate(finishEpoch);

        item.setContent(content);
        item.setRemarks(remarks);
        item.setEditDate(DateTimeUtils.DateTimeToLong());

        Integer updateRows = itemsService.edit(id, item);
        model.addAttribute("updateRows", updateRows);

        return "redirect:/plan/items/list";
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
        PlanItems info = itemsService.getById(id);
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
