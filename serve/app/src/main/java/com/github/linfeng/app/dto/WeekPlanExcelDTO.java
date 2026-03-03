package com.github.linfeng.app.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class WeekPlanExcelDTO {

    @ExcelProperty("标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @ExcelProperty("内容")
    private String content;

    @ExcelProperty("年份")
    @NotNull(message = "年份不能为空")
    private Integer year;

    @ExcelProperty("周数")
    @NotNull(message = "周数不能为空")
    @Min(value = 1, message = "周数必须在1-53之间")
    @Max(value = 53, message = "周数必须在1-53之间")
    private Integer weekNumber;

    @ExcelProperty("优先级")
    private String priority;

    @ExcelProperty("标签")
    private String tags;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Integer getWeekNumber() { return weekNumber; }
    public void setWeekNumber(Integer weekNumber) { this.weekNumber = weekNumber; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
}
