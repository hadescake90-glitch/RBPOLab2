package com.example.rbpolab2.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FundingDecision {
    private Long id;
    private Long applicationId;
    private BigDecimal approvedAmount;
    private String status; // "APPROVED", "REJECTED", "PAID"
    private LocalDateTime createdAt;

    public FundingDecision() {}

    public FundingDecision(Long id, Long applicationId, BigDecimal approvedAmount, String status, LocalDateTime createdAt) {
        this.id = id;
        this.applicationId = applicationId;
        this.approvedAmount = approvedAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public BigDecimal getApprovedAmount() { return approvedAmount; }
    public void setApprovedAmount(BigDecimal approvedAmount) { this.approvedAmount = approvedAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}