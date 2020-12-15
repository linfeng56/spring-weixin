package com.github.linfeng.view.mini.login;

import com.github.linfeng.view.base.BaseResponseView;

/**
 * 解析会话密钥结果
 *
 * @author 黄麟峰
 */
public class JsCodeToSessionResponseView extends BaseResponseView {
    /**
     * 用户唯一标识
     */
    private String openId = "";
    /**
     * 会话密钥
     */
    private String sessionKey = "";
    /**
     * 用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。
     */
    private String unionId = "";

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public JsCodeToSessionResponseView(String json) {
        super(json);
        if ("0".equals(getErrCode()) || "".equals(getErrCode())) {
            this.openId = this.json.getString("openid");
            this.sessionKey = this.json.getString("session_key");
            this.unionId = this.json.getString("unionid");
        }
    }


}
