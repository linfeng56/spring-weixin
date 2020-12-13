package com.github.linfeng.view.auth;

import com.github.linfeng.view.base.BaseRequestView;

import java.util.Map;

/**
 * 用户同意授权，获取code
 *
 * @author 黄麟峰
 */
public class CodeRequestView extends BaseRequestView {


    /**
     * (必须)授权后重定向的回调链接地址， 请使用 urlEncode 对链接进行处理,应当使用https链接来确保授权code的安全性
     */
    private String redirectUri = "";
    /**
     * (必须)返回类型，请填写code
     */
    private String responseType = "code";
    /**
     * (必须)应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息 ）
     */
    private String scope = "";
    /**
     * (可选)重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
     */
    private String state = "getCode";
    /**
     * (必须)无论直接打开还是做页面302重定向时候，必须带此参数
     */
    private String wechatRedirect = "#wechat_redirect";

    /**
     * 请求地址
     */
    private String url = "https://open.weixin.qq.com/connect/oauth2/authorize";

    @Override
    public String getUrl() {
        return url;
    }


    @Override
    public String buildGet() {
        // 格式:https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
        return String.format("%s?appid=%s&redirect_uri=%s&response_type=%s&scope=%s&state=%s",
            getUrl(), getAppid(), getRedirectUri(), getResponseType(), getScope(), getState());

    }

    @Override
    public Map<String, String> buildPostData() {
        return null;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getResponseType() {
        return responseType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWechatRedirect() {
        return wechatRedirect;
    }

}
