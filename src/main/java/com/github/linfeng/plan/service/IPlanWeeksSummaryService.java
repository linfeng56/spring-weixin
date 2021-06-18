package com.github.linfeng.plan.service;

import java.util.List;
import com.github.linfeng.plan.entity.PlanWeekSummary;

/**
 * 服务类.
 *
 * @author 黄麟峰
 */
public interface IPlanWeeksSummaryService {

    List<PlanWeekSummary> list();

    PlanWeekSummary getByWeekId(Integer weekId);


    PlanWeekSummary getByUserId(Integer userId);

    Integer add(PlanWeekSummary weeks);

    /**
     * 列出指定周的周总结
     *
     * @param searchText 总结内容包含的文字
     * @return 周总结列表
     */
    List<PlanWeekSummary> list(String searchText);

    Integer update(Integer id, PlanWeekSummary weeks);

    PlanWeekSummary getById(Integer id);
}
