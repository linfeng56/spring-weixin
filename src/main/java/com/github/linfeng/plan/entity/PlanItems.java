package com.github.linfeng.plan.entity;

import java.io.Serializable;

/**
 * .
 *
 * @author 黄麟峰
 */
public class PlanItems implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 类型:1需求,2任务,3bug,4文档,5其他
     */
    private Integer jobType;

    /**
     * 工作编号
     */
    private Integer jobNum;

    /**
     * 工作名称
     */
    private String title;

    /**
     * 工作内容
     */
    private String content;

    /**
     * 开始日期
     */
    private Integer beginDate;

    /**
     * 完成日期
     */
    private Integer endDate;

    /**
     * 整体完成日期
     */
    private Integer jobFinishDate;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建时间
     */
    private Integer createDate;

    /**
     * 最后编辑时间
     */
    private Integer editDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    public Integer getJobNum() {
        return jobNum;
    }

    public void setJobNum(Integer jobNum) {
        this.jobNum = jobNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Integer getJobFinishDate() {
        return jobFinishDate;
    }

    public void setJobFinishDate(Integer jobFinishDate) {
        this.jobFinishDate = jobFinishDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Integer createDate) {
        this.createDate = createDate;
    }

    public Integer getEditDate() {
        return editDate;
    }

    public void setEditDate(Integer editDate) {
        this.editDate = editDate;
    }

    @Override
    public String toString() {
        return "PlanItems{" +
            "id=" + id +
            ", userId=" + userId +
            ", jobType=" + jobType +
            ", jobNum=" + jobNum +
            ", title=" + title +
            ", content=" + content +
            ", beginDate=" + beginDate +
            ", endDate=" + endDate +
            ", jobFinishDate=" + jobFinishDate +
            ", remarks=" + remarks +
            ", createDate=" + createDate +
            ", editDate=" + editDate +
            "}";
    }
}
