package com.github.linfeng.view;

/**
 * 基础类
 *
 * @author 黄麟峰
 * @date 2020-12-01 13:46
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