package com.github.linfeng.plan.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import com.github.linfeng.plan.entity.CalendarEventObject;
import com.github.linfeng.plan.holder.LoginUserHolder;
import com.github.linfeng.plan.service.IPlanItemsService;
import com.github.linfeng.plan.service.IPlanUsersService;
import com.github.linfeng.plan.service.IPlanWeeksService;
import com.github.linfeng.plan.view.LoginUser;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/plan/calendar")
public class PlanCalendarController extends BasePlanController {

    private final IPlanWeeksService weeksService;
    private final IPlanItemsService itemsService;
    private final IPlanUsersService planUsersService;

    @Autowired
    public PlanCalendarController(IPlanWeeksService weeksService, IPlanItemsService itemsService,
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
        Integer itemCount = itemsService.count();
        Integer planRate = (planCount * 100) / itemCount;
        model.addAttribute("planRate", planRate);

        // 总用户数
        Integer userCount = planUsersService.count();
        model.addAttribute("userCount", userCount);

        return "plan/calendar/index";
    }

    // @RequiresRoles("normaladmin")
    @RequestMapping(value = "/feed", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object feed(HttpServletRequest request, HttpServletResponse response) {
        List<CalendarEventObject> ret = new ArrayList<>();
        ret.add(new CalendarEventObject("全天事件", System.currentTimeMillis(),
            "#f56954", "#f56954", true));
        ret.add(new CalendarEventObject("长事件", System.currentTimeMillis(),
            System.currentTimeMillis() + 5 * 60 * 60 * 1000,
            "#f56954", "#f56954", false));

        // 两次转换目的是去除为null的项
        return JSON.parse(JSON.toJSONString(ret, true));
    }
}
