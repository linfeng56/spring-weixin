package com.github.linfeng.plan.controller;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import com.github.linfeng.plan.entity.CalendarEventObject;
import com.github.linfeng.plan.entity.PlanItems;
import com.github.linfeng.plan.entity.PlanUsers;
import com.github.linfeng.plan.holder.LoginUserHolder;
import com.github.linfeng.plan.service.IPlanItemsService;
import com.github.linfeng.plan.service.IPlanUsersService;
import com.github.linfeng.plan.service.IPlanWeeksService;
import com.github.linfeng.plan.view.JobType;
import com.github.linfeng.plan.view.LoginUser;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/plan/calendar")
public class PlanCalendarController extends BasePlanController {

    private final Random random = new Random();

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
        Map<String, String> jobTypes = JobType.toMap();
        model.addAttribute("jobTypes", jobTypes);

        List<PlanUsers> users = planUsersService.list();
        model.addAttribute("users", users);
        model.addAttribute("weeks", weeksService.list());

        String date = request.getParameter("date");
        if (date == null) {
            date = "";
        }
        model.addAttribute("date", date);

        // 周计划数
        Integer planCount = weeksService.count();
        model.addAttribute("planCount", planCount);

        // 计划项总数
        Integer itemCount = itemsService.cnt();
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
    public Object feed(@RequestParam("start") String start, @RequestParam("end") String end, HttpServletRequest request,
        HttpServletResponse response) {
        List<CalendarEventObject> ret = new ArrayList<>();

        Long startDate = OffsetDateTime.parse(start).toInstant().getEpochSecond() * 1000;
        Long endDate = OffsetDateTime.parse(end).toInstant().getEpochSecond() * 1000;

        String urlFormat = request.getContextPath() + "/plan/items/edit/";

        List<PlanItems> items = itemsService.list(startDate, endDate);
        for (PlanItems item : items) {
            String color = color(item.getId() % 6);
            boolean allDay = false;
            if (item.getEndDate() - item.getBeginDate() > 24 * 60 * 60 * 1000L) {
                allDay = true;
            }
            CalendarEventObject evt = new CalendarEventObject(item.getTitle(), item.getBeginDate(), item.getEndDate(),
                color, color, allDay);
            evt.setUrl(urlFormat + item.getId());
            ret.add(evt);
        }

        // 两次转换目的是去除为null的项
        return JSON.parse(JSON.toJSONString(ret, true));
    }


    private String color(int index) {
        String[] colors = new String[]{"#f56954", //red
            "#f39c12", //yellow
            "#0073b7", //Blue
            "#00c0ef", //Info (aqua)
            "#00a65a", //Success (green)
            "#3c8dbc", //Primary (light-blue)
        };
        if (index < 0 || index >= colors.length) {
            index = random.nextInt(colors.length);
        }
        return colors[index];
    }
}
