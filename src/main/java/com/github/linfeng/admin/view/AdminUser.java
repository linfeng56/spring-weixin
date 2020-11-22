package com.github.linfeng.admin.view;

/**
 * 管理员
 *
 * @author 黄麟峰
 * @date 2021-03-19 23:54
 */
public class AdminUser {

    /**
     * 用户编号
     */
    private Integer adminId;

    /**
     * 用户名
     */
    private String userName;

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
