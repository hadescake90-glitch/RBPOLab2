package com.example.rbpolab2.repository;

import com.example.rbpolab2.model.ExpertReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertReviewRepository extends JpaRepository<ExpertReview, Long> {
    List<ExpertReview> findByApplicationId(Long applicationId);
    List<ExpertReview> findByExpertId(Long expertId);
    boolean existsByApplicationIdAndExpertId(Long applicationId, Long expertId);
    void deleteByApplicationId(Long applicationId);
    void deleteByExpertId(Long expertId);
}