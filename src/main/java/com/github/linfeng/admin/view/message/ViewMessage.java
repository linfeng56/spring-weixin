package com.github.linfeng.admin.view.message;

import java.util.Date;

/**
 * 页面信息.
 *
 * @author 黄麟峰
 * @date 2021-03-12
 */
public class ViewMessage {

    /**
     * 信息类型
     */
    private MessageType type;

    /**
     * 信息标题
     */
    private String title;

    /**
     * 信息内容
     */
    private String content;

    /**
     * 时间
     */
    private Date date;

    /**
     * 模板路径
     */
    private String viewPath;

    /**
     * 跳转地址
     */
    private String toUrl;

    /**
     * 一般信息
     *
     * @param title   标题
     * @param content 内容
     */
    public ViewMessage(String title, String content) {
        this.type = MessageType.INFO;
        this.title = title;
        this.content = content;
        this.date = new Date();
    }

    /**
     * 构造信息
     *
     * @param type    信息类型
     * @param title   标题
     * @param content 内容
     */
    public ViewMessage(MessageType type, String title, String content) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.date = new Date();
    }

    /**
     * 构造信息
     *
     * @param type     信息类型
     * @param title    标题
     * @param content  内容
     * @param viewPath 模板路径
     */
    public ViewMessage(MessageType type, String title, String content, String viewPath) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.viewPath = viewPath;
        this.date = new Date();
    }

    /**
     * 构造信息
     *
     * @param type     信息类型
     * @param title    标题
     * @param content  内容
     * @param viewPath 模板路径
     * @param toUrl    跳转地址
     */
    public ViewMessage(MessageType type, String title, String content, String viewPath, String toUrl) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.viewPath = viewPath;
        this.toUrl = toUrl;
        this.date = new Date();
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getViewPath() {
        return viewPath;
    }

    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }

    public String getToUrl() {
        return toUrl;
    }

    public void setToUrl(String toUrl) {
        this.toUrl = toUrl;
    }
}
