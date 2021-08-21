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

    /**
     * 更新周计划
     *
     * @param id    周计划编号
     * @param weeks 周计划
     * @return 受影响记录数, 大于0成功, 等于0失败.
     */
    Integer update(Integer id, PlanWeeks weeks);

    /**
     * 更新总结内容
     *
     * @param id          周计划编号
     * @param summary     总结内容
     * @param summaryDate 提交总结时间
     * @return 受影响记录数, 大于0成功, 等于0失败.
     */
    Integer updateSummary(Integer id, String summary, Long summaryDate);
}
