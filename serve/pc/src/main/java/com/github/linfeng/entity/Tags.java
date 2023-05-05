package com.github.linfeng.entity;

import java.io.Serializable;

/**
 * 标签.
 *
 * @author 黄麟峰
 */
public class Tags implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标签编号
     */
    private Integer tagId;

    /**
     * 标签
     */
    private String tagName;

    /**
     * 引用次数
     */
    private Integer relNum;

    /**
     * 创建时间
     */
    private Integer createTime;

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getRelNum() {
        return relNum;
    }

    public void setRelNum(Integer relNum) {
        this.relNum = relNum;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Tags{" +
            "tagId=" + tagId +
            ", tagName=" + tagName +
            ", relNum=" + relNum +
            ", createTime=" + createTime +
            "}";
    }
}
