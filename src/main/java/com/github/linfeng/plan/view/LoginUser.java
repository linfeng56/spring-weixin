package com.github.linfeng.plan.view;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import com.alibaba.fastjson.JSON;

import org.apache.commons.codec.binary.Base64;

/**
 * 登录用户信息
 *
 * @author 黄麟峰
 * @date 2021-08-10
 */
public class LoginUser implements Serializable {

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
    private Long loginDate;

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

    public Long getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Long loginDate) {
        this.loginDate = loginDate;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LoginUser{");
        sb.append("userId=").append(userId);
        sb.append(", loginName='").append(loginName).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", loginDate=").append(loginDate);
        sb.append('}');
        return sb.toString();
    }

    /**
     * 登录用户加密
     *
     * @return 加密后的字串
     */
    public String encode() {
        return Base64.encodeBase64URLSafeString(JSON.toJSONString(this).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 登录用户解密
     *
     * @param userInfo 待解密字串
     * @return 登录用户或null
     */
    public static LoginUser decode(String userInfo) {
        String user = new String(Base64.decodeBase64(userInfo));
        LoginUser loginUser = JSON.parseObject(user, LoginUser.class);
        if (loginUser != null && loginUser.getUserId() > 0) {
            return loginUser;
        } else {
            return null;
        }
    }
}
