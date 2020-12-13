package com.github.linfeng.view.base;

import java.util.Map;

/**
 * 请求基础类
 *
 * @author 黄麟峰
 */
public abstract class BaseRequestView extends BaseView {

    /**
     * 请求地址
     *
     * @return 请求地址
     */
    public abstract String getUrl();

    /**
     * 构建get请求地址
     *
     * @return 请求地址
     */
    public abstract String buildGet();

    /**
     * 构建post请求数据
     *
     * @return 请求数据
     */
    public abstract Map<String, String> buildPostData();
}
