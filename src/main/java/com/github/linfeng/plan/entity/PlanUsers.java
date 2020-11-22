package com.github.linfeng.plan.entity;

import java.io.Serializable;

/**
 * .
 *
 * @author 黄麟峰
 */
public class PlanUsers implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 用户登录名
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String loginPassword;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 创建时间
     */
    private Integer createDate;

    /**
     * 登录时间
     */
    private Integer loginDate;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Integer createDate) {
        this.createDate = createDate;
    }

    public Integer getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Integer loginDate) {
        this.loginDate = loginDate;
    }

    @Override
    public String toString() {
        return "PlanUsers{" +
            "userId=" + userId +
            ", loginName=" + loginName +
            ", loginPassword=" + loginPassword +
            ", userName=" + userName +
            ", createDate=" + createDate +
            ", loginDate=" + loginDate +
            "}";
    }
}
