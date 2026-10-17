package com.example.rbpolab2.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GrantCompetitionDto {
    private String title;
    private BigDecimal budget;
    private LocalDateTime submissionDeadline;
    private LocalDateTime evaluationDeadline;
    private String status;

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
}