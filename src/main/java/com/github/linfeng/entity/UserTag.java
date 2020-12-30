package com.github.linfeng.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 用户标签.
 *
 * @author 黄麟峰
 * @since 2020-12-30
 */
@TableName("spwx_user_tag")
public class UserTag implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private Integer uid;

    /**
     * 标签编号
     */
    private Integer tagId;

    /**
     * 标签
     */
    private String tagName;

    /**
     * 贴标时间
     */
    private Integer bindTime;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
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
    public Integer getBindTime() {
        return bindTime;
    }

    public void setBindTime(Integer bindTime) {
        this.bindTime = bindTime;
    }

    @Override
    public String toString() {
        return "UserTag{" +
            "uid=" + uid +
            ", tagId=" + tagId +
            ", tagName=" + tagName +
            ", bindTime=" + bindTime +
        "}";
    }
}
