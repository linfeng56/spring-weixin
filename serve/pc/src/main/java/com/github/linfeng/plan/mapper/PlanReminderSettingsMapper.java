package com.github.linfeng.plan.mapper;

import com.github.linfeng.plan.entity.PlanReminderSettings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlanReminderSettingsMapper {

    PlanReminderSettings getByWeekId(@Param("weekId") Integer weekId);

    Integer insert(@Param("settings") PlanReminderSettings settings);

    Integer update(@Param("settings") PlanReminderSettings settings);
}
