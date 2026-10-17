package com.example.rbpolab2.model;

import java.time.LocalDateTime;

public class ExpertReview {
    private Long id;
    private Long applicationId;
    private Long expertId;
    private Integer score;
    private String comment;
    private LocalDateTime createdAt;

    public ExpertReview() {}

    public ExpertReview(Long id, Long applicationId, Long expertId, Integer score, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.applicationId = applicationId;
        this.expertId = expertId;
        this.score = score;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public Long getExpertId() { return expertId; }
    public void setExpertId(Long expertId) { this.expertId = expertId; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}