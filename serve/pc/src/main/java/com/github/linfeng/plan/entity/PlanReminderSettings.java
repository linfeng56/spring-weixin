package com.github.linfeng.plan.entity;

import java.io.Serializable;

public class PlanReminderSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer settingId;
    private Integer weekId;
    private Integer userId;
    private Integer remindBefore1day;
    private Integer remindOnDay;
    private Integer remindedBefore1day;
    private Integer remindedOnDay;
    private Long createTime;
    private Long updateTime;

    public Integer getSettingId() { return settingId; }
    public void setSettingId(Integer settingId) { this.settingId = settingId; }
    public Integer getWeekId() { return weekId; }
    public void setWeekId(Integer weekId) { this.weekId = weekId; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getRemindBefore1day() { return remindBefore1day; }
    public void setRemindBefore1day(Integer remindBefore1day) { this.remindBefore1day = remindBefore1day; }
    public Integer getRemindOnDay() { return remindOnDay; }
    public void setRemindOnDay(Integer remindOnDay) { this.remindOnDay = remindOnDay; }
    public Integer getRemindedBefore1day() { return remindedBefore1day; }
    public void setRemindedBefore1day(Integer remindedBefore1day) { this.remindedBefore1day = remindedBefore1day; }
    public Integer getRemindedOnDay() { return remindedOnDay; }
    public void setRemindedOnDay(Integer remindedOnDay) { this.remindedOnDay = remindedOnDay; }
    public Long getCreateTime() { return createTime; }
    public void setCreateTime(Long createTime) { this.createTime = createTime; }
    public Long getUpdateTime() { return updateTime; }
    public void setUpdateTime(Long updateTime) { this.updateTime = updateTime; }
}
