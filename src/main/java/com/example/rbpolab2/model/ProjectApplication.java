package com.example.rbpolab2.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "project_applications")
public class ProjectApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "competition_id", nullable = false)
    private Long competitionId;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(name = "requested_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal requestedAmount;

    @Column(name = "status", nullable = false)
    private String status; // "SUBMITTED", "UNDER_REVIEW", "APPROVED", "REJECTED", "FUNDED"

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public ProjectApplication() {}

    public ProjectApplication(Long id, Long competitionId, Long authorId, String projectName, BigDecimal requestedAmount, String status, LocalDateTime createdAt) {
        this.id = id;
        this.competitionId = competitionId;
        this.authorId = authorId;
        this.projectName = projectName;
        this.requestedAmount = requestedAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCompetitionId() { return competitionId; }
    public void setCompetitionId(Long competitionId) { this.competitionId = competitionId; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public BigDecimal getRequestedAmount() { return requestedAmount; }
    public void setRequestedAmount(BigDecimal requestedAmount) { this.requestedAmount = requestedAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}