package com.github.linfeng.plan.mapper;

import com.github.linfeng.plan.entity.PlanSubtasks;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlanSubtasksMapper {

    List<PlanSubtasks> listByWeekId(@Param("weekId") Integer weekId);

    PlanSubtasks getById(@Param("id") Integer id);

    Integer add(@Param("subtask") PlanSubtasks subtask);

    Integer update(@Param("id") Integer id, @Param("subtask") PlanSubtasks subtask);

    Integer delete(@Param("id") Integer id);
}
