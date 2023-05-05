package com.github.linfeng.admin.view.message;

/**
 * 页面信息.
 *
 * @author 黄麟峰
 * @date 2021-12-05
 */
public final class AjaxViewMessage extends ViewMessage {

    /**
     * 信息类型编码
     */
    private int code;

    /**
     * 一般信息
     *
     * @param title   标题
     * @param content 内容
     */
    public AjaxViewMessage(String title, String content) {
        super(MessageType.INFO, title, content);
        this.code = MessageType.INFO.getCode();
    }

    /**
     * 构造信息
     *
     * @param type    信息类型
     * @param title   标题
     * @param content 内容
     */
    public AjaxViewMessage(MessageType type, String title, String content) {
        super(MessageType.INFO, title, content);
        this.code = type.getCode();
    }

    /**
     * 构造信息
     *
     * @param type     信息类型
     * @param title    标题
     * @param content  内容
     * @param viewPath 模板路径
     */
    public AjaxViewMessage(MessageType type, String title, String content, String viewPath) {
        super(MessageType.INFO, title, content, viewPath);
        this.code = type.getCode();
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
    public AjaxViewMessage(MessageType type, String title, String content, String viewPath, String toUrl) {
        super(MessageType.INFO, title, content, viewPath, toUrl);
        this.code = type.getCode();
    }

    public int getCode() {
        return code;
    }
}
