package com.github.linfeng.plan.mapper;

import java.util.List;
import com.github.linfeng.plan.entity.PlanWeeks;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Mapper 接口.
 *
 * @author 黄麟峰
 */
@Repository
public interface PlanWeeksMapper {

    List<PlanWeeks> list();

    PlanWeeks getById(@Param("id") Integer id);

    Integer add(@Param("week") PlanWeeks weeks);

    /**
     * 列出指定周的周计划
     *
     * @param searchText 计划名称包含的文字
     * @return 周计划列表
     */
    List<PlanWeeks> listByTitle(String searchText);

    Integer update(@Param("id") Integer id, @Param("week") PlanWeeks week);
}
