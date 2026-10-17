package com.example.rbpolab2.dto;

import java.math.BigDecimal;

public class FundingDecisionDto {
    private Long applicationId;
    private BigDecimal approvedAmount;
    private String status;

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public BigDecimal getApprovedAmount() { return approvedAmount; }
    public void setApprovedAmount(BigDecimal approvedAmount) { this.approvedAmount = approvedAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}