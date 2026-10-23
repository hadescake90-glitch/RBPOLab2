package com.example.rbpolab2.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "funding_decisions",
        uniqueConstraints = @UniqueConstraint(name = "uk_decision_application", columnNames = {"application_id"})
)
public class FundingDecision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "application_id", nullable = false)
    private Long applicationId;

    @Column(name = "approved_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal approvedAmount;

    @Column(name = "status", nullable = false)
    private String status; // "APPROVED", "REJECTED", "PAID", "CANCELLED"

    @Column(name = "created_at", nullable = false)
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