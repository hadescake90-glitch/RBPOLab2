package com.example.rbpolab2.repository;

import com.example.rbpolab2.model.FundingDecision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FundingDecisionRepository extends JpaRepository<FundingDecision, Long> {
    Optional<FundingDecision> findByApplicationId(Long applicationId);
    void deleteByApplicationId(Long applicationId);
}