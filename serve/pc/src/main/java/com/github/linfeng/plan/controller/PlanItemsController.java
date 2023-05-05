package com.github.linfeng.plan.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import com.github.linfeng.plan.entity.PlanItems;
import com.github.linfeng.plan.entity.PlanUsers;
import com.github.linfeng.plan.service.IPlanItemsService;
import com.github.linfeng.plan.service.IPlanUsersService;
import com.github.linfeng.plan.service.IPlanWeeksService;
import com.github.linfeng.plan.view.JobType;
import com.github.linfeng.plan.view.ResponseView;
import com.github.linfeng.utils.DateTimeUtils;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 周计划任务项控制器.
 *
 * @author 黄麟峰
 */
@Controller
@RequestMapping("/plan/items")
public class PlanItemsController extends BasePlanController {

    /**
     * 周计划项服务
     */
    private final IPlanItemsService itemsService;

    /**
     * 用户服务
     */
    private final IPlanUsersService usersService;

    /**
     * 周计划服务
     */
    private final IPlanWeeksService weeksService;

    @Autowired
    public PlanItemsController(IPlanItemsService itemsService,
        IPlanUsersService usersService,
        IPlanWeeksService weeksService) {
        this.itemsService = itemsService;
        this.usersService = usersService;
        this.weeksService = weeksService;
    }


    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "plan/items/index";
    }


    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value = "searchWeekId", required = false) Integer searchWeekId,
        @RequestParam(value = "searchUserId", required = false) Integer searchUserId,
        @RequestParam(value = "searchText", required = false) String searchText,
        HttpServletRequest request, HttpServletResponse response) {

        List<PlanItems> list = itemsService.listByWeek(searchWeekId, searchUserId, searchText);

        model.addAttribute("items", list);
        model.addAttribute("types", JobType.allMap());
        model.addAttribute("weeks", weeksService.list());
        model.addAttribute("planUsers", usersService.list());
        model.addAttribute("searchUserId", searchUserId != null ? searchUserId : 0);
        model.addAttribute("searchWeekId", searchWeekId != null ? searchWeekId : 0);

        return "plan/items/list";
    }

    @RequiresRoles("normaladmin")
    @RequestMapping("/cnt")
    @ResponseBody
    public ResponseView<Integer> cnt() {
        Integer cnt = itemsService.cnt();
        if (cnt == null) {
            cnt = 0;
        }
        return new ResponseView<>(200, "OK", cnt);
    }


    @GetMapping(value = "/add")
    public String add(Model model, HttpServletRequest request, HttpServletResponse response) {

        Map<String, String> jobTypes = JobType.toMap();
        model.addAttribute("jobTypes", jobTypes);

        List<PlanUsers> users = usersService.list();
        model.addAttribute("users", users);
        model.addAttribute("weeks", weeksService.list());
        return "plan/items/add";
    }

    @PostMapping(value = "/doAdd")
    @ResponseBody
    public ResponseView<Integer> doAdd(Model model, Integer itemJobType, Integer itemJobNum,
        String itemTitle, Integer itemUserId,
        Integer itemWeekId, String itemBegin, String itemEnd, String itemFinish, String content, String remarks) {

        ResponseView<Integer> errorMessage = validForm(itemTitle, itemWeekId,
            itemBegin, itemEnd);
        if (errorMessage != null) {
            return errorMessage;
        }

        PlanItems item = buildPlanItems(itemJobType, itemJobNum, itemTitle, itemUserId, itemWeekId, itemBegin, itemEnd,
            itemFinish, content,
            remarks);
        item.setCreateDate(DateTimeUtils.DateTimeToLong());
        item.setEditDate(DateTimeUtils.DateTimeToLong());

        Integer itemId = itemsService.add(item);

        return new ResponseView<>(200, "操作成功", itemId);
    }

    /**
     * 构建周计划项
     *
     * @param itemJobType 计划项类型
     * @param itemJobNum  计划项编号
     * @param itemTitle   标题
     * @param itemUserId  用户编号
     * @param itemWeekId  周计划编号
     * @param itemBegin   开始日期
     * @param itemEnd     结束日期
     * @param itemFinish  完成日期
     * @param content     内容
     * @param remarks     备注
     * @return 周计划项
     */
    private PlanItems buildPlanItems(Integer itemJobType, Integer itemJobNum, String itemTitle, Integer itemUserId,
        Integer itemWeekId, String itemBegin, String itemEnd, String itemFinish, String content, String remarks) {

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
        return item;
    }

    /**
     * 表单数据验证
     *
     * @param itemTitle  标题
     * @param itemWeekId 周计划编号
     * @param itemBegin  开始日期
     * @param itemEnd    结束日期
     * @return 异常提示对象或null
     */
    private ResponseView<Integer> validForm(String itemTitle, Integer itemWeekId, String itemBegin,
        String itemEnd) {
        StringBuilder errorMessage = new StringBuilder(25);
        if (!StringUtils.hasText(itemTitle)) {
            errorMessage.append("标题不能为空!").append("\\n");
        }
        if (!StringUtils.hasText(itemBegin)) {
            errorMessage.append("请选择开始日期!").append("\\n");
        }
        if (!StringUtils.hasText(itemEnd)) {
            errorMessage.append("请选择截止日期!").append("\\n");
        }
        if (itemWeekId == null || itemWeekId < 1) {
            errorMessage.append("请选择所属周计划!").append("\\n");
        }
        if (StringUtils.hasText(errorMessage)) {
            return new ResponseView<>(401, errorMessage.toString());
        }
        return null;
    }


    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
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

    @PostMapping(value = "/doEdit/{id}")
    @ResponseBody
    public ResponseView<Integer> doEdit(@PathVariable("id") Integer id, Model model, Integer itemJobType,
        Integer itemJobNum,
        String itemTitle, Integer itemUserId, Integer itemWeekId, String itemBegin, String itemEnd,
        String itemFinish, String content, String remarks) {

        ResponseView<Integer> errorMessage = validForm(itemTitle, itemWeekId,
            itemBegin, itemEnd);
        if (errorMessage != null) {
            return errorMessage;
        }

        PlanItems item = buildPlanItems(itemJobType, itemJobNum, itemTitle, itemUserId, itemWeekId, itemBegin, itemEnd,
            itemFinish, content, remarks);
        item.setEditDate(DateTimeUtils.DateTimeToLong());

        Integer updateRows = itemsService.edit(id, item);
        if (updateRows > 0) {
            return new ResponseView<>(200, "操作成功", id);
        } else {
            return new ResponseView<>(500, "操作失败", id);
        }
    }


    @GetMapping("/detail/{id}")
    @ResponseBody
    public String list(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
        HttpServletResponse response) {
        Map<String, Object> ret = new HashMap<>(6);
        if (null == id || id <= 0) {
            ret.put("retCode", "fail");
            ret.put("errMsg", "error id");
            return JSON.toJSONString(ret);
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
        return JSON.toJSONString(ret);
    }
}
