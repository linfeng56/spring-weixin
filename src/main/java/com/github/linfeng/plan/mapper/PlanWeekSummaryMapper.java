package com.github.linfeng.plan.mapper;

import java.util.List;
import com.github.linfeng.plan.entity.PlanWeekSummary;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Mapper 接口.
 *
 * @author 黄麟峰
 */
@Repository
public interface PlanWeekSummaryMapper {

    List<PlanWeekSummary> list();

    PlanWeekSummary getByWeekId(@Param("weekId") Integer weekId);

    Integer add(@Param("summary") PlanWeekSummary summary);

    /**
     * 列出指定周的周计划
     *
     * @param searchText 计划名称包含的文字
     * @return 周计划列表
     */
    List<PlanWeekSummary> listBySummary(String searchText);

    Integer update(@Param("id") Integer id, @Param("summary") PlanWeekSummary summary);

    PlanWeekSummary getByUserId(@Param("userId") Integer userId);

    PlanWeekSummary getById(@Param("id") Integer id);
}
