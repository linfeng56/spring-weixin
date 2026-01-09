package com.github.linfeng.app.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ProgressUpdateRequest {

    @NotNull(message = "进度值不能为空")
    @Min(value = 0, message = "进度值不能小于0")
    @Max(value = 100, message = "进度值不能大于100")
    private Integer progress;

    private String remark;

    public Integer getProgress() { return progress; }
    public void setProgress(Integer progress) { this.progress = progress; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
