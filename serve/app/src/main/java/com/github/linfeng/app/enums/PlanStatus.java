package com.github.linfeng.app.enums;

import java.util.HashMap;
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
        ALLOWED_TRANSITIONS.put(DRAFT, Set.of(PENDING_APPROVAL, IN_PROGRESS, CANCELLED));
        ALLOWED_TRANSITIONS.put(PENDING_APPROVAL, Set.of(IN_PROGRESS, REJECTED, CANCELLED));
        ALLOWED_TRANSITIONS.put(IN_PROGRESS, Set.of(PAUSED, COMPLETED, CANCELLED));
        ALLOWED_TRANSITIONS.put(PAUSED, Set.of(IN_PROGRESS, CANCELLED));
        ALLOWED_TRANSITIONS.put(COMPLETED, Set.of(ARCHIVED));
        ALLOWED_TRANSITIONS.put(REJECTED, Set.of(DRAFT));
        ALLOWED_TRANSITIONS.put(CANCELLED, Set.of(DRAFT));
        ALLOWED_TRANSITIONS.put(ARCHIVED, Set.of());
    }

    PlanStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public boolean canTransitionTo(PlanStatus target) {
        return ALLOWED_TRANSITIONS.getOrDefault(this, Set.of()).contains(target);
    }
}
