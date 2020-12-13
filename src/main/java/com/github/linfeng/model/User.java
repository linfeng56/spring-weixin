package com.github.linfeng.model;

import java.util.Date;

/**
 * 用户基本信息
 *
 * @author 黄麟峰
 */
public class User {

    private Integer id;
    private String userName;
    private String nickName;
    private String pwd;
    private String phone;
    private String openId;
    private String accessToken;
    private String refreshToken;
    private Date accessTokenExpire;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getAccessTokenExpire() {
        return accessTokenExpire;
    }

    public void setAccessTokenExpire(Date accessTokenExpire) {
        this.accessTokenExpire = accessTokenExpire;
    }
}
