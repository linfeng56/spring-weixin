package com.github.linfeng.plan.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * 应用模板请求DTO
 */
public class ApplyTemplateRequest implements Serializable {

    private Map<String, String> variables;

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }
}
