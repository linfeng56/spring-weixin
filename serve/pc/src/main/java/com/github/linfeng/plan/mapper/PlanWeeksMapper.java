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

    PlanWeeks getByIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);

    Integer add(@Param("week") PlanWeeks weeks);

    /**
     * 列出指定周的周计划
     *
     * @param searchText 计划名称包含的文字
     * @return 周计划列表
     */
    List<PlanWeeks> listByTitle(String searchText);

    /**
     * 按用户ID查询周计划
     */
    List<PlanWeeks> listByUserId(@Param("userId") Integer userId);

    /**
     * 按用户ID和状态查询周计划
     */
    List<PlanWeeks> listByUserIdAndStatus(@Param("userId") Integer userId, @Param("status") Integer status);

    Integer update(@Param("id") Integer id, @Param("week") PlanWeeks week);

    Integer updateSummary(@Param("id") Integer id, @Param("summary") String summary,@Param("summaryDate") Long summaryDate);

    Integer deleteByIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);

    Integer count();
}
