package com.github.linfeng.plan.mapper;

import com.github.linfeng.plan.entity.PlanChangeHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlanChangeHistoryMapper {

    List<PlanChangeHistory> listByWeekId(@Param("weekId") Integer weekId);

    Integer add(@Param("history") PlanChangeHistory history);
}
