package com.github.linfeng.plan.view;

import java.util.HashMap;
import java.util.Map;

/**
 * 工作类型
 *
 * @author 黄麟峰
 * @date 2021-03-31
 */
public enum JobType {
    /**
     * 无
     */
    NULL(0, "未指定"),
    /**
     * 需求
     */
    STORY(1, "需求"),
    /**
     * 任务
     */
    TASK(2, "任务"),
    /**
     * BUG
     */
    BUG(3, "BUG"),
    /**
     * 文档
     */
    DOC(4, "文档"),
    /**
     * 其他
     */
    OTHER(5, "其他");

    private final Integer id;
    private final String name;

    JobType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 返回HashMap类型的枚举信息
     *
     * @return 检举信息
     */
    public static Map<String, String> allMap() {
        Map<String, String> ret = new HashMap<>(6);
        for (JobType jobType : JobType.values()) {
            ret.put("k_" + jobType.id, jobType.name);
        }

        return ret;
    }


    /**
     * 返回List类型的枚举信息
     *
     * @return 检举信息
     */
    public static Map<String, String> toMap() {
        Map<String, String> ret = new HashMap<>(6);
        for (JobType jobType : JobType.values()) {
            ret.put(jobType.id.toString(), jobType.name);
        }

        return ret;
    }

    /**
     * 通过值获取实例
     *
     * @param id 值
     * @return 枚举对象
     */
    public static JobType getById(Integer id) {
        for (JobType jobType : JobType.values()) {
            if (jobType.id.equals(id)) {
                return jobType;
            }
        }
        return JobType.NULL;
    }

    /**
     * 通过值获取名称
     *
     * @param id 值
     * @return 枚举的名称
     */
    public static String getNameById(Integer id) {
        for (JobType jobType : JobType.values()) {
            if (jobType.id.equals(id)) {
                return jobType.name;
            }
        }
        return JobType.NULL.name;
    }
}
