package com.github.linfeng.view;

import java.util.Map;

/**
 * 微信用户基本信息请求.
 *
 * @author 黄麟峰
 * @date 2020-12-03
 */
public class UserInfoRequestView extends BaseRequestView {

    /**
     * 用户的唯一标识
     */
    private String openid = "";

    /**
     * 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
     */
    private String accessToken = "";

    /**
     * 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
     */
    private String lang = "zh_CN";

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

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    private String url = "https://api.weixin.qq.com/sns/userinfo";

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String buildGet() {
        return String.format("%s?access_token=%s&openid=%s&lang=%s",
            getUrl(), getAccessToken(), getOpenid(), getLang());
    }

    @Override
    public Map<String, String> buildPostData() {
        return null;
    }
}
