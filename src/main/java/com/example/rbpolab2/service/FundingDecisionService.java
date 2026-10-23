package com.example.rbpolab2.service;

import com.example.rbpolab2.dto.FundingDecisionDto;
import com.example.rbpolab2.model.FundingDecision;
import com.example.rbpolab2.repository.FundingDecisionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FundingDecisionService {

    private final FundingDecisionRepository decisionRepository;

    public FundingDecisionService(FundingDecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
    }

    public List<FundingDecision> findAll() {
        return decisionRepository.findAll();
    }

    public Optional<FundingDecision> findById(Long id) {
        return decisionRepository.findById(id);
    }

    @Transactional
    public FundingDecision create(FundingDecisionDto dto) {
        FundingDecision d = new FundingDecision();
        d.setApplicationId(dto.getApplicationId());
        d.setApprovedAmount(dto.getApprovedAmount());
        d.setStatus(dto.getStatus() != null ? dto.getStatus() : "APPROVED");
        d.setCreatedAt(LocalDateTime.now());
        return decisionRepository.save(d);
    }

    @Transactional
    public Optional<FundingDecision> update(Long id, FundingDecisionDto dto) {
        return decisionRepository.findById(id).map(d -> {
            if (dto.getApprovedAmount() != null) d.setApprovedAmount(dto.getApprovedAmount());
            if (dto.getStatus() != null) d.setStatus(dto.getStatus());
            return decisionRepository.save(d);
        });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (!decisionRepository.existsById(id)) return false;
        decisionRepository.deleteById(id);
        return true;
    }
}