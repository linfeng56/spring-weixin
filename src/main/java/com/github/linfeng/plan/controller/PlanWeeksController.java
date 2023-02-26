package com.github.linfeng.plan.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import com.github.linfeng.plan.entity.PlanWeeks;
import com.github.linfeng.plan.holder.LoginUserHolder;
import com.github.linfeng.plan.service.IPlanItemsService;
import com.github.linfeng.plan.service.IPlanUsersService;
import com.github.linfeng.plan.service.IPlanWeeksService;
import com.github.linfeng.plan.view.LoginUser;
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
 * 前端控制器.
 *
 * @author 黄麟峰
 */
@Controller
@RequestMapping("/plan/weeks")
public class PlanWeeksController extends BasePlanController {

    /**
     * 周计划服务
     */
    private final IPlanWeeksService weeksService;
    private final IPlanItemsService itemsService;
    private final IPlanUsersService planUsersService;

    @Autowired
    public PlanWeeksController(IPlanWeeksService weeksService, IPlanItemsService itemsService,
        IPlanUsersService planUsersService) {
        this.weeksService = weeksService;
        this.itemsService = itemsService;
        this.planUsersService = planUsersService;
    }

    @RequiresRoles("normaladmin")
    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        LoginUser loginUser = LoginUserHolder.getLoginUser();
        model.addAttribute("admin", loginUser);

        // 周计划数
        Integer planCount = weeksService.count();
        model.addAttribute("planCount", planCount);

        // 计划项总数
        Integer itemCount = itemsService.cnt();
        Integer planRate = (planCount*100)/itemCount;
        model.addAttribute("planRate", planRate);

        // 总用户数
        Integer userCount = planUsersService.count();
        model.addAttribute("userCount", userCount);

        return "plan/weeks/index";
    }


    @RequiresRoles("normaladmin")
    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value = "table_search", required = false) String searchText,
        HttpServletRequest request, HttpServletResponse response) {
        LoginUser loginUser = LoginUserHolder.getLoginUser();
        model.addAttribute("admin", loginUser);

        List<PlanWeeks> list;
        if (StringUtils.hasText(searchText)) {
            list = weeksService.list(searchText);
        } else {
            list = weeksService.list();
        }
        model.addAttribute("weeks", list);

        return "plan/weeks/list";
    }


    @RequiresRoles("normaladmin")
    @GetMapping(value = "/add")
    public String add(Model model, HttpServletRequest request, HttpServletResponse response) {
        LoginUser loginUser = LoginUserHolder.getLoginUser();
        model.addAttribute("admin", loginUser);
        return "plan/weeks/add";
    }

    @RequiresRoles("normaladmin")
    @PostMapping(value = "/doAdd")
    @ResponseBody
    public ResponseView<Integer> doAdd(String weekTitle, String weekBegin, String weekEnd,
        String remarks) {

        ResponseView<Integer> errorMessage = validForm(weekTitle, weekBegin, weekEnd);
        if (errorMessage != null) {
            return errorMessage;
        }

        PlanWeeks weeks = buildPlanWeeks(weekTitle, weekBegin, weekEnd, remarks);
        weeks.setCreateDate(DateTimeUtils.DateTimeToLong());

        Integer weekId = weeksService.add(weeks);

        return new ResponseView<>(200, "操作成功", weekId);
    }

    /**
     * 验证表单数据
     *
     * @param weekTitle 标题
     * @param weekBegin 开始日期
     * @param weekEnd   结束日期
     * @return 异常提示对象或null
     */
    private ResponseView<Integer> validForm(String weekTitle, String weekBegin, String weekEnd) {
        StringBuilder errorMessage = new StringBuilder(25);
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


    @RequiresRoles("normaladmin")
    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        LoginUser loginUser = LoginUserHolder.getLoginUser();
        model.addAttribute("admin", loginUser);
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

    @RequiresRoles("normaladmin")
    @PostMapping(value = "/doEdit/{id}")
    @ResponseBody
    public ResponseView<Integer> doEdit(@PathVariable("id") Integer id, Model model, String weekTitle, String weekBegin,
        String weekEnd, String remarks) {

        ResponseView<Integer> errorMessage = validForm(weekTitle, weekBegin, weekEnd);
        if (errorMessage != null) {
            return errorMessage;
        }

        PlanWeeks weeks = buildPlanWeeks(weekTitle, weekBegin, weekEnd, remarks);

        Integer updateRows = weeksService.update(id, weeks);
        if (updateRows > 0) {
            return new ResponseView<>(200, "操作成功", id);
        } else {
            return new ResponseView<>(500, "操作失败", id);
        }
    }

    /**
     * 构建周计划
     *
     * @param weekTitle 标题
     * @param weekBegin 开始日期
     * @param weekEnd   结束日期
     * @param remarks   备注
     * @return 周计划
     */
    private PlanWeeks buildPlanWeeks(String weekTitle, String weekBegin, String weekEnd, String remarks) {
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
        return weeks;
    }

    @RequiresRoles("normaladmin")
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
        PlanWeeks info = weeksService.getById(id);
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


    @RequiresRoles("normaladmin")
    @GetMapping(value = "/summary/{id}")
    @ResponseBody
    public ResponseView summary(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
        HttpServletResponse response) {
        if (null == id || id <= 0) {
            return new ResponseView<>(500, "参数不合法");
        }
        PlanWeeks info = weeksService.getById(id);
        if (null != info) {
            return new ResponseView<>(200, "成功", info);
        } else {
            return new ResponseView<>(400, "记录不存在");
        }
    }

    @RequiresRoles("normaladmin")
    @PostMapping(value = "/summary/{id}")
    @ResponseBody
    public ResponseView<PlanWeeks> summaryEdit(@PathVariable("id") Integer id, Model model, HttpServletRequest request,
        HttpServletResponse response) {
        if (null == id || id <= 0) {
            return new ResponseView<>(500, "参数不合法");
        }
        PlanWeeks info = weeksService.getById(id);
        if (null == info) {
            return new ResponseView<>(400, "记录不存在");
        }
        String summary = request.getParameter("summary");
        Long summaryDate = DateTimeUtils.DateTimeToLong();
        Integer result = weeksService.updateSummary(id, summary, summaryDate);
        if (result > 0) {
            return new ResponseView<>(200, "保存成功");
        } else {
            return new ResponseView<>(500, "保存失败");
        }
    }
}
