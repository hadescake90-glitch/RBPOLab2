package com.example.rbpolab2.service;

import com.example.rbpolab2.dto.FundingDecisionDto;
import com.example.rbpolab2.model.FundingDecision;
import com.example.rbpolab2.repository.FundingDecisionRepository;
import com.example.rbpolab2.repository.ProjectApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FundingDecisionService {

    private final FundingDecisionRepository decisionRepository;
    private final ProjectApplicationRepository applicationRepository;

    public FundingDecisionService(FundingDecisionRepository decisionRepository,
                                  ProjectApplicationRepository applicationRepository) {
        this.decisionRepository = decisionRepository;
        this.applicationRepository = applicationRepository;
    }

    public List<FundingDecision> getAll() {
        return decisionRepository.findAll();
    }

    public FundingDecision getById(Long id) {
        return decisionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Решение о финансировании с id " + id + " не найдено"));
    }

    public FundingDecision create(FundingDecisionDto dto) {
        if (dto.getApplicationId() == null || !applicationRepository.existsById(dto.getApplicationId())) {
            throw new IllegalArgumentException("Заявка с id " + dto.getApplicationId() + " не найдена");
        }
        FundingDecision decision = new FundingDecision(
                null,
                dto.getApplicationId(),
                dto.getApprovedAmount(),
                dto.getStatus() != null ? dto.getStatus() : "APPROVED",
                LocalDateTime.now()
        );
        return decisionRepository.save(decision);
    }

    public FundingDecision update(Long id, FundingDecisionDto dto) {
        FundingDecision decision = getById(id);
        if (dto.getApprovedAmount() != null) decision.setApprovedAmount(dto.getApprovedAmount());
        if (dto.getStatus() != null) decision.setStatus(dto.getStatus());
        return decisionRepository.save(decision);
    }

    public void delete(Long id) {
        if (!decisionRepository.deleteById(id)) {
            throw new NoSuchElementException("Решение о финансировании с id " + id + " не найдено");
        }
    }
}