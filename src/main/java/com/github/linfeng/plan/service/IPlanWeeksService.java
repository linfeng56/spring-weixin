package com.github.linfeng.plan.service;

import java.util.List;
import com.github.linfeng.plan.entity.PlanWeeks;

/**
 * 服务类.
 *
 * @author 黄麟峰
 */
public interface IPlanWeeksService {

    List<PlanWeeks> list();

    PlanWeeks getById(Integer id);

    Integer add(PlanWeeks weeks);

    /**
     * 列出指定周的周计划
     *
     * @param searchText 计划名称包含的文字
     * @return 周计划列表
     */
    List<PlanWeeks> list(String searchText);

    Integer update(Integer id, PlanWeeks weeks);
}
