package com.github.linfeng.view.auth;

import com.github.linfeng.view.base.BaseResponseView;

/**
 * Access Token 请求结果
 *
 * @author 黄麟峰
 */
public class AccessTokenResponseView extends BaseResponseView {

    /**
     * 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
     */
    private String accessToken = "";
    /**
     * access_token接口调用凭证超时时间，单位（秒）
     */
    private String expiresIn = "";
    /**
     * 用户刷新access_token
     */
    private String refreshToken = "";
    /**
     * 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
     */
    private String openid = "";
    /**
     * 用户授权的作用域，使用逗号（,）分隔
     */
    private String scope = "";

    public AccessTokenResponseView(String json) {
        /*
         * 正确时返回的JSON数据包如下：
         *
         * {
         *   "access_token":"ACCESS_TOKEN",
         *   "expires_in":7200,
         *   "refresh_token":"REFRESH_TOKEN",
         *   "openid":"OPENID",
         *   "scope":"SCOPE"
         * }
         */
        super(json);
        if ("".equals(this.getErrCode())) {
            accessToken = this.json.getString("receive_access_token");
            expiresIn = this.json.getString("receive_expires_in");
            refreshToken = this.json.getString("receive_refresh_token");
            openid = this.json.getString("receive_openid");
            scope = this.json.getString("receive_scope");
        }
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getOpenid() {
        return openid;
    }

    public String getScope() {
        return scope;
    }
}
