package com.github.linfeng.plan.entity;

import java.io.Serializable;

/**
 * .
 *
 * @author 黄麟峰
 */
public class PlanWeekSummary implements Serializable {

    private static final long serialVersionUID = 626L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 周计划编号
     */
    private Integer weekId;

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 标题
     */
    private String summary;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建时间
     */
    private Long createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWeekId() {
        return weekId;
    }

    public void setWeekId(Integer weekId) {
        this.weekId = weekId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "PlanWeekSummary{" +
            "weekId=" + weekId +
            ", userId=" + userId +
            ", summary='" + summary + '\'' +
            ", remarks='" + remarks + '\'' +
            ", createDate=" + createDate +
            '}';
    }
}
