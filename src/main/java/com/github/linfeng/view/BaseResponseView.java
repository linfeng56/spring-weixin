package com.github.linfeng.view;

/**
 * 接收结果
 *
 * @author 黄麟峰
 * @date 2020-12-01 14:00
 */
public abstract class BaseResponseView {

    /**
     * 错误编码
     */
    private String errCode = "";

    /**
     * 错误信息
     */
    private String errMsg = "";

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
