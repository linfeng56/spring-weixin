package com.github.linfeng.view;

import java.util.Map;

/**
 * 刷新token请求
 *
 * @author 黄麟峰
 * @date 2020-12-03
 */
public class RefreshTokenRequestView extends BaseRequestView {


    /**
     * (必须) 公众号的唯一标识
     */
    private String appid = "";
    /**
     * (必须) 填写为refresh_token
     */
    private String grantType = "refresh_token";
    /**
     * (必须) 填写通过access_token获取到的refresh_token参数
     */
    private String refreshToken = "";

    @Override
    public String getAppid() {
        return appid;
    }

    @Override
    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    private String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token";

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String buildGet() {
        return String.format("%s?appid=%s&grant_type=%s&refresh_token=%s",
            getUrl(), getAppid(), getGrantType(), getRefreshToken());
    }

    @Override
    public Map<String, String> buildPostData() {
        return null;
    }
}
