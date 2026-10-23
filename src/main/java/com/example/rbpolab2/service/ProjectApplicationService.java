package com.example.rbpolab2.service;

import com.example.rbpolab2.dto.ProjectApplicationDto;
import com.example.rbpolab2.model.FundingDecision;
import com.example.rbpolab2.model.GrantCompetition;
import com.example.rbpolab2.model.ProjectApplication;
import com.example.rbpolab2.repository.ExpertReviewRepository;
import com.example.rbpolab2.repository.FundingDecisionRepository;
import com.example.rbpolab2.repository.GrantCompetitionRepository;
import com.example.rbpolab2.repository.ProjectApplicationRepository;
import com.example.rbpolab2.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectApplicationService {

    private final ProjectApplicationRepository applicationRepository;
    private final GrantCompetitionRepository competitionRepository;
    private final UserRepository userRepository;
    private final ExpertReviewRepository reviewRepository;
    private final FundingDecisionRepository decisionRepository;

    public ProjectApplicationService(ProjectApplicationRepository applicationRepository,
                                     GrantCompetitionRepository competitionRepository,
                                     UserRepository userRepository,
                                     ExpertReviewRepository reviewRepository,
                                     FundingDecisionRepository decisionRepository) {
        this.applicationRepository = applicationRepository;
        this.competitionRepository = competitionRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.decisionRepository = decisionRepository;
    }

    public List<ProjectApplication> findAll() {
        return applicationRepository.findAll();
    }

    public Optional<ProjectApplication> findById(Long id) {
        return applicationRepository.findById(id);
    }

    @Transactional
    public ProjectApplication create(ProjectApplicationDto dto) {
        ProjectApplication app = new ProjectApplication();
        app.setCompetitionId(dto.getCompetitionId());
        app.setAuthorId(dto.getAuthorId());
        app.setProjectName(dto.getProjectName());
        app.setRequestedAmount(dto.getRequestedAmount());
        app.setStatus(dto.getStatus() != null ? dto.getStatus() : "SUBMITTED");
        app.setCreatedAt(LocalDateTime.now());
        return applicationRepository.save(app);
    }

    @Transactional
    public Optional<ProjectApplication> update(Long id, ProjectApplicationDto dto) {
        return applicationRepository.findById(id).map(app -> {
            if (dto.getProjectName() != null) app.setProjectName(dto.getProjectName());
            if (dto.getRequestedAmount() != null) app.setRequestedAmount(dto.getRequestedAmount());
            if (dto.getStatus() != null) app.setStatus(dto.getStatus());
            return applicationRepository.save(app);
        });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (!applicationRepository.existsById(id)) {
            return false;
        }
        reviewRepository.findByApplicationId(id).forEach(reviewRepository::delete);
        decisionRepository.findByApplicationId(id).ifPresent(decisionRepository::delete);
        applicationRepository.deleteById(id);
        return true;
    }

    // БИЗНЕС-ОПЕРАЦИЯ 1: Подача заявки с проверкой условий
    @Transactional
    public ProjectApplication submitApplication(ProjectApplicationDto dto) {
        GrantCompetition comp = competitionRepository.findById(dto.getCompetitionId())
                .orElseThrow(() -> new IllegalArgumentException("Конкурс не найден"));

        if (!"OPEN".equalsIgnoreCase(comp.getStatus())) {
            throw new IllegalStateException("Конкурс закрыт для приема заявок");
        }
        if (!userRepository.existsById(dto.getAuthorId())) {
            throw new IllegalArgumentException("Автор не зарегистрирован");
        }
        if (dto.getRequestedAmount() != null && comp.getBudget() != null && dto.getRequestedAmount().compareTo(comp.getBudget()) > 0) {
            throw new IllegalArgumentException("Запрашиваемая сумма превышает общий бюджет конкурса");
        }

        ProjectApplication app = new ProjectApplication();
        app.setCompetitionId(comp.getId());
        app.setAuthorId(dto.getAuthorId());
        app.setProjectName(dto.getProjectName());
        app.setRequestedAmount(dto.getRequestedAmount());
        app.setStatus("SUBMITTED");
        app.setCreatedAt(LocalDateTime.now());
        return applicationRepository.save(app);
    }

    // БИЗНЕС-ОПЕРАЦИЯ 4: Выплата гранта
    @Transactional
    public ProjectApplication disburseGrant(Long applicationId) {
        ProjectApplication app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Заявка не найдена"));

        if (!"APPROVED".equalsIgnoreCase(app.getStatus())) {
            throw new IllegalStateException("Финансирование возможно только для одобренных заявок");
        }

        FundingDecision decision = decisionRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new IllegalStateException("Решение о финансировании отсутствует"));

        decision.setStatus("PAID");
        decisionRepository.save(decision);

        app.setStatus("FUNDED");
        return applicationRepository.save(app);
    }

    // БИЗНЕС-ОПЕРАЦИЯ 5: Отзыв заявки
    @Transactional
    public ProjectApplication withdrawApplication(Long applicationId) {
        ProjectApplication app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Заявка не найдена"));

        if ("FUNDED".equalsIgnoreCase(app.getStatus())) {
            throw new IllegalStateException("Нельзя отозвать профинансированный проект");
        }

        decisionRepository.findByApplicationId(applicationId).ifPresent(decision -> {
            decision.setStatus("CANCELLED");
            decisionRepository.save(decision);
        });

        app.setStatus("REJECTED");
        return applicationRepository.save(app);
    }
}