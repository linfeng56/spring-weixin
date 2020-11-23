package com.github.linfeng.model;

/**
 * 用户基本信息
 *
 * @author 黄麟峰
 * @date 2020-11-23 21:15
 */
public class User {

    private Integer id;
    private String userName;
    private String nickName;
    private String pwd;

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
}
