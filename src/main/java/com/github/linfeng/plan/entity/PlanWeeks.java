package com.github.linfeng.plan.entity;

import java.io.Serializable;

/**
 * .
 *
 * @author 黄麟峰
 */
public class PlanWeeks implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer weekId;

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 开始日期
     */
    private Integer beginDate;

    /**
     * 截止日期
     */
    private Integer endDate;

    /**
     * 创建时间
     */
    private Integer createDate;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Integer beginDate) {
        this.beginDate = beginDate;
    }

    public Integer getEndDate() {
        return endDate;
    }

    public void setEndDate(Integer endDate) {
        this.endDate = endDate;
    }

    public Integer getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Integer createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "PlanWeeks{" +
            "weekId=" + weekId +
            ", userId=" + userId +
            ", title=" + title +
            ", remarks=" + remarks +
            ", beginDate=" + beginDate +
            ", endDate=" + endDate +
            ", createDate=" + createDate +
            "}";
    }
}
