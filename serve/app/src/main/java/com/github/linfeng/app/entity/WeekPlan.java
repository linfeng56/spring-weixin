package com.github.linfeng.app.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "week_plan")
public class WeekPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Integer year;

    @Column(name = "week_number", nullable = false)
    private Integer weekNumber;

    @Column(nullable = false, length = 20)
    private String status = "DRAFT";

    @Column(name = "current_progress", nullable = false)
    private Integer currentProgress = 0;

    @Column(length = 10)
    private String priority = "MEDIUM";

    @Column(length = 500)
    private String tags;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Integer getWeekNumber() { return weekNumber; }
    public void setWeekNumber(Integer weekNumber) { this.weekNumber = weekNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getCurrentProgress() { return currentProgress; }
    public void setCurrentProgress(Integer currentProgress) { this.currentProgress = currentProgress; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
