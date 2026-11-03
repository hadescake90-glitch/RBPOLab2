package com.example.rbpolab2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "expert_reviews",
        uniqueConstraints = @UniqueConstraint(name = "uk_application_expert", columnNames = {"application_id", "expert_id"})
)
public class ExpertReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "application_id", nullable = false)
    private Long applicationId;

    @Column(name = "expert_id", nullable = false)
    private Long expertId;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "comment", length = 1000)
    private String comment;

    @Column(name = "created_at", nullable = false)
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