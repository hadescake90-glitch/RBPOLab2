package com.example.rbpolab2.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "grant_competitions")
public class GrantCompetition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "budget", nullable = false, precision = 15, scale = 2)
    private BigDecimal budget;

    @Column(name = "submission_deadline")
    private LocalDateTime submissionDeadline;

    @Column(name = "evaluation_deadline")
    private LocalDateTime evaluationDeadline;

    @Column(name = "status", nullable = false)
    private String status; // "OPEN", "EVALUATION", "CLOSED"

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public GrantCompetition() {}

    public GrantCompetition(Long id, String title, BigDecimal budget, LocalDateTime submissionDeadline, LocalDateTime evaluationDeadline, String status, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.budget = budget;
        this.submissionDeadline = submissionDeadline;
        this.evaluationDeadline = evaluationDeadline;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public BigDecimal getBudget() { return budget; }
    public void setBudget(BigDecimal budget) { this.budget = budget; }

    public LocalDateTime getSubmissionDeadline() { return submissionDeadline; }
    public void setSubmissionDeadline(LocalDateTime submissionDeadline) { this.submissionDeadline = submissionDeadline; }

    public LocalDateTime getEvaluationDeadline() { return evaluationDeadline; }
    public void setEvaluationDeadline(LocalDateTime evaluationDeadline) { this.evaluationDeadline = evaluationDeadline; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}