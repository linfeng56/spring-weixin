package com.github.linfeng.plan.mapper;

import java.util.List;
import com.github.linfeng.plan.entity.PlanItems;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Mapper 接口.
 *
 * @author 黄麟峰
 */
@Repository
public interface PlanItemsMapper {

    List<PlanItems> list(@Param("start") Long start, @Param("end") Long end);

    PlanItems getById(@Param("id") Integer id);

    Integer add(@Param("item") PlanItems item);

    List<PlanItems> listByWeek(@Param("weekId") Integer weekId, @Param("userId") Integer userId,
        @Param("searchText") String searchText);

    Integer edit(@Param("id") Integer id, @Param("item") PlanItems item);

    Integer count();

}
