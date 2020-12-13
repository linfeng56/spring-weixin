package com.github.linfeng.view.auth;

import com.github.linfeng.view.base.BaseRequestView;

import java.util.Map;

/**
 * 检验授权凭证（access_token）是否有效.
 *
 * @author 黄麟峰
 * @date 2020-12-03
 */
public class CheckAccessTokenRequestView extends BaseRequestView {

    /**
     * 用户的唯一标识
     */
    private String openid = "";

    /**
     * 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
     */
    private String accessToken = "";

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    private String url = "https://api.weixin.qq.com/sns/auth";

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String buildGet() {
        return String.format("%s?access_token=%s&openid=%s", getUrl(), getAccessToken(), getOpenid());
    }

    @Override
    public Map<String, String> buildPostData() {
        return null;
    }
}
