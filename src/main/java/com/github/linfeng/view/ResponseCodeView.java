package com.github.linfeng.view;

/**
 * 用户同意授权，获取code
 *
 * @author 黄麟峰
 * @date 2020-12-01 14:02
 */
public class ResponseCodeView extends BaseResponseView {

    /**
     * code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。
     */
    private String code = "";

    /**
     * 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
     */
    private String state = "";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
