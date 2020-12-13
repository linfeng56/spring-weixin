package com.github.linfeng.view.mini.login;

import com.github.linfeng.view.base.BaseRequestView;

import java.util.Map;

/**
 * 获取传话密钥
 *
 * @author 黄麟峰
 */
public class JsCodeToSessionRequestView extends BaseRequestView {

    /**
     * (必须)小程序 appSecret
     */
    private String secret = "";
    /**
     * (必须)登录时获取的 code
     */
    private String jsCode = "";
    /**
     * (必须)授权类型，此处只需填写 authorization_code
     */
    private String grantType = "authorization_code";

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getJsCode() {
        return jsCode;
    }

    public void setJsCode(String jsCode) {
        this.jsCode = jsCode;
    }

    public String getGrantType() {
        return grantType;
    }

    @Override
    public String getUrl() {
        return "https://api.weixin.qq.com/sns/jscode2session";
    }

    @Override
    public String buildGet() {
        return String.format("%s?appid=%s&secret=%s&js_code=%s&grant_type=%s",
            getUrl(), getAppid(), getSecret(), getJsCode(), getGrantType());
    }

    @Override
    public Map<String, String> buildPostData() {
        return null;
    }
}
