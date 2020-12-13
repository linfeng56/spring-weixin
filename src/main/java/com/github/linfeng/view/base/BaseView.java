package com.github.linfeng.view.base;

/**
 * 基础类
 *
 * @author 黄麟峰
 */
public abstract class BaseView {

    /**
     * (必须)公众号的唯一标识
     */
    private String appid = "";

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
