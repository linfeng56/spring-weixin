package com.github.linfeng.app.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum PlanStatus {
    DRAFT("草稿"),
    PENDING_APPROVAL("待审批"),
    IN_PROGRESS("进行中"),
    PAUSED("已暂停"),
    COMPLETED("已完成"),
    CANCELLED("已取消"),
    REJECTED("已拒绝"),
    ARCHIVED("已归档");

    private final String label;

    private static final Map<PlanStatus, Set<PlanStatus>> ALLOWED_TRANSITIONS = new HashMap<>();

    static {
        ALLOWED_TRANSITIONS.put(DRAFT, new HashSet<>(Arrays.asList(PENDING_APPROVAL, IN_PROGRESS, CANCELLED)));
        ALLOWED_TRANSITIONS.put(PENDING_APPROVAL, new HashSet<>(Arrays.asList(IN_PROGRESS, REJECTED, CANCELLED)));
        ALLOWED_TRANSITIONS.put(IN_PROGRESS, new HashSet<>(Arrays.asList(PAUSED, COMPLETED, CANCELLED)));
        ALLOWED_TRANSITIONS.put(PAUSED, new HashSet<>(Arrays.asList(IN_PROGRESS, CANCELLED)));
        ALLOWED_TRANSITIONS.put(COMPLETED, new HashSet<>(Arrays.asList(ARCHIVED)));
        ALLOWED_TRANSITIONS.put(REJECTED, new HashSet<>(Arrays.asList(DRAFT)));
        ALLOWED_TRANSITIONS.put(CANCELLED, new HashSet<>(Arrays.asList(DRAFT)));
        ALLOWED_TRANSITIONS.put(ARCHIVED, new HashSet<>(Arrays.asList()));
    }

    PlanStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public boolean canTransitionTo(PlanStatus target) {
        return ALLOWED_TRANSITIONS.getOrDefault(this, new HashSet<>(Arrays.asList())).contains(target);
    }
}
