package com.github.linfeng.view.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 接收结果
 *
 * @author 黄麟峰
 * @date 2020-12-01 14:00
 */
public abstract class BaseResponseView {

    public BaseResponseView(String json) {
        if (null != json && !"".equals(json)) {
            this.json = JSON.parseObject(json);
            if (this.json.containsKey("errcode")) {
                errCode = this.json.getString("errcode");
                errMsg = this.json.getString("errmsg");
            }
        }
    }

    protected JSONObject json;

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
