package com.example.rbpolab2.dto;

import java.math.BigDecimal;

public class ProjectApplicationDto {
    private Long competitionId;
    private Long authorId;
    private String projectName;
    private BigDecimal requestedAmount;
    private String status;

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
}