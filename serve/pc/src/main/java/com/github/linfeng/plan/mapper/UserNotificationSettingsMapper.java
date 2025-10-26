package com.github.linfeng.plan.mapper;

import com.github.linfeng.plan.entity.UserNotificationSettings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserNotificationSettingsMapper {

    UserNotificationSettings getByUserId(@Param("userId") Integer userId);

    Integer insert(@Param("settings") UserNotificationSettings settings);

    Integer update(@Param("settings") UserNotificationSettings settings);
}
