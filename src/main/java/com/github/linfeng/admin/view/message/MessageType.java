package com.github.linfeng.admin.view.message;

/**
 * 页面信息类型.
 *
 * @author 黄麟峰
 * @date 2021-03-12
 */
public enum MessageType {
    /**
     * 成功
     */
    SUCCESS(200),
    /**
     * 失败
     */
    ERROR(500),
    /**
     * 一般信息
     */
    INFO(100),
    /**
     * 提示
     */
    TIP(99);

    /**
     * 信息类型编码
     */
    private int code;

    MessageType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
