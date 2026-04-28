package com.github.linfeng.plan.enums;

/**
 * 模板分类枚举
 *
 * @author 黄麟峰
 */
public enum TemplateCategory {
    WORK("WORK", "工作"),
    STUDY("STUDY", "学习"),
    LIFE("LIFE", "生活"),
    OTHER("OTHER", "其他");

    private final String code;
    private final String desc;

    TemplateCategory(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static TemplateCategory fromCode(String code) {
        for (TemplateCategory category : values()) {
            if (category.code.equals(code)) {
                return category;
            }
        }
        return OTHER;
    }
}
