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
    private Long beginDate;

    /**
     * 截止日期
     */
    private Long endDate;

    /**
     * 创建时间
     */
    private Long createDate;

    /**
     * 总结
     */
    private String summary;

    /**
     * 总结时间
     */
    private Long summaryDate;

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

    public Long getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Long beginDate) {
        this.beginDate = beginDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getSummaryDate() {
        return summaryDate;
    }

    public void setSummaryDate(Long summaryDate) {
        this.summaryDate = summaryDate;
    }

    @Override
    public String toString() {
        return "PlanWeeks{" +
            "weekId=" + weekId +
            ", userId=" + userId +
            ", title='" + title + '\'' +
            ", remarks='" + remarks + '\'' +
            ", beginDate=" + beginDate +
            ", endDate=" + endDate +
            ", createDate=" + createDate +
            ", summary='" + summary + '\'' +
            ", summaryDate=" + summaryDate +
            '}';
    }
}
