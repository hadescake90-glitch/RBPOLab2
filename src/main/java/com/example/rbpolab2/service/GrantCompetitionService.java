package com.example.rbpolab2.service;

import com.example.rbpolab2.dto.GrantCompetitionDto;
import com.example.rbpolab2.model.FundingDecision;
import com.example.rbpolab2.model.GrantCompetition;
import com.example.rbpolab2.model.ProjectApplication;
import com.example.rbpolab2.repository.FundingDecisionRepository;
import com.example.rbpolab2.repository.GrantCompetitionRepository;
import com.example.rbpolab2.repository.ProjectApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GrantCompetitionService {

    private final GrantCompetitionRepository competitionRepository;
    private final ProjectApplicationRepository applicationRepository;
    private final FundingDecisionRepository decisionRepository;

    public GrantCompetitionService(GrantCompetitionRepository competitionRepository,
                                   ProjectApplicationRepository applicationRepository,
                                   FundingDecisionRepository decisionRepository) {
        this.competitionRepository = competitionRepository;
        this.applicationRepository = applicationRepository;
        this.decisionRepository = decisionRepository;
    }

    public List<GrantCompetition> findAll() {
        return competitionRepository.findAll();
    }

    public Optional<GrantCompetition> findById(Long id) {
        return competitionRepository.findById(id);
    }

    @Transactional
    public GrantCompetition create(GrantCompetitionDto dto) {
        GrantCompetition comp = new GrantCompetition();
        comp.setTitle(dto.getTitle());
        comp.setBudget(dto.getBudget());
        comp.setSubmissionDeadline(dto.getSubmissionDeadline());
        comp.setEvaluationDeadline(dto.getEvaluationDeadline());
        comp.setStatus(dto.getStatus() != null ? dto.getStatus() : "OPEN");
        comp.setCreatedAt(LocalDateTime.now());
        return competitionRepository.save(comp);
    }

    @Transactional
    public Optional<GrantCompetition> update(Long id, GrantCompetitionDto dto) {
        return competitionRepository.findById(id).map(comp -> {
            if (dto.getTitle() != null) comp.setTitle(dto.getTitle());
            if (dto.getBudget() != null) comp.setBudget(dto.getBudget());
            if (dto.getStatus() != null) comp.setStatus(dto.getStatus());
            return competitionRepository.save(comp);
        });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (!competitionRepository.existsById(id)) {
            return false;
        }
        List<ProjectApplication> apps = applicationRepository.findByCompetitionId(id);
        for (ProjectApplication app : apps) {
            decisionRepository.findByApplicationId(app.getId()).ifPresent(decisionRepository::delete);
            applicationRepository.delete(app);
        }
        competitionRepository.deleteById(id);
        return true;
    }

    // БИЗНЕС-ОПЕРАЦИЯ 3: Завершение конкурса и авто-распределение финансирования
    @Transactional
    public GrantCompetition finalizeCompetition(Long competitionId) {
        GrantCompetition comp = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new IllegalArgumentException("Конкурс не найден"));

        if ("CLOSED".equalsIgnoreCase(comp.getStatus())) {
            throw new IllegalStateException("Конкурс уже закрыт");
        }

        List<ProjectApplication> applications = applicationRepository.findByCompetitionId(competitionId);
        BigDecimal remainingBudget = comp.getBudget();

        for (ProjectApplication app : applications) {
            if ("SUBMITTED".equalsIgnoreCase(app.getStatus()) || "UNDER_REVIEW".equalsIgnoreCase(app.getStatus())) {
                if (remainingBudget != null && app.getRequestedAmount() != null && remainingBudget.compareTo(app.getRequestedAmount()) >= 0) {
                    remainingBudget = remainingBudget.subtract(app.getRequestedAmount());
                    app.setStatus("APPROVED");

                    FundingDecision decision = new FundingDecision();
                    decision.setApplicationId(app.getId());
                    decision.setApprovedAmount(app.getRequestedAmount());
                    decision.setStatus("APPROVED");
                    decision.setCreatedAt(LocalDateTime.now());
                    decisionRepository.save(decision);
                } else {
                    app.setStatus("REJECTED");
                }
                applicationRepository.save(app);
            }
        }

        comp.setStatus("CLOSED");
        return competitionRepository.save(comp);
    }
}