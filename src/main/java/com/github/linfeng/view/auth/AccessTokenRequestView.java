package com.github.linfeng.view.auth;

import com.github.linfeng.view.base.BaseRequestView;

import java.util.Map;

/**
 * access token请求
 *
 * @author 黄麟峰
 * @date 2020-12-02 16:29
 */
public class AccessTokenRequestView extends BaseRequestView {

    /**
     * (必须)公众号的appsecret
     */
    private String secret = "";
    /**
     * (必须)填写第一步获取的code参数
     */
    private String code = "";
    /**
     * (必须)填写为authorization_code
     */
    private String grantType = "authorization_code";


    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGrantType() {
        return grantType;
    }

    private String url = "https://api.weixin.qq.com/sns/oauth2/access_token";

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String buildGet() {
        return String.format("%s?appid=%s&secret=%s&code=%s&grant_type=%s",
            getUrl(), getAppid(), getSecret(), getCode(), getGrantType());
    }

    @Override
    public Map<String, String> buildPostData() {
        return null;
    }
}
