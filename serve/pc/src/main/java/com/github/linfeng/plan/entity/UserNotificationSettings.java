package com.github.linfeng.plan.entity;

import java.io.Serializable;

public class UserNotificationSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;
    private String email;
    private String dingtalkWebhook;
    private Integer enableEmail;
    private Integer enableDingtalk;
    private Long createTime;
    private Long updateTime;

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDingtalkWebhook() { return dingtalkWebhook; }
    public void setDingtalkWebhook(String dingtalkWebhook) { this.dingtalkWebhook = dingtalkWebhook; }
    public Integer getEnableEmail() { return enableEmail; }
    public void setEnableEmail(Integer enableEmail) { this.enableEmail = enableEmail; }
    public Integer getEnableDingtalk() { return enableDingtalk; }
    public void setEnableDingtalk(Integer enableDingtalk) { this.enableDingtalk = enableDingtalk; }
    public Long getCreateTime() { return createTime; }
    public void setCreateTime(Long createTime) { this.createTime = createTime; }
    public Long getUpdateTime() { return updateTime; }
    public void setUpdateTime(Long updateTime) { this.updateTime = updateTime; }
}
