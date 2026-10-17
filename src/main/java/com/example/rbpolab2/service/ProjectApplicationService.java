package com.example.rbpolab2.service;

import com.example.rbpolab2.dto.ProjectApplicationDto;
import com.example.rbpolab2.model.ProjectApplication;
import com.example.rbpolab2.repository.ExpertReviewRepository;
import com.example.rbpolab2.repository.FundingDecisionRepository;
import com.example.rbpolab2.repository.GrantCompetitionRepository;
import com.example.rbpolab2.repository.ProjectApplicationRepository;
import com.example.rbpolab2.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

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

    public List<ProjectApplication> getAll() {
        return applicationRepository.findAll();
    }

    public ProjectApplication getById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Заявка с id " + id + " не найдена"));
    }

    public ProjectApplication create(ProjectApplicationDto dto) {
        if (dto.getCompetitionId() == null || !competitionRepository.existsById(dto.getCompetitionId())) {
            throw new IllegalArgumentException("Конкурс с id " + dto.getCompetitionId() + " не найден");
        }
        if (dto.getAuthorId() == null || !userRepository.existsById(dto.getAuthorId())) {
            throw new IllegalArgumentException("Автор с id " + dto.getAuthorId() + " не найден");
        }
        ProjectApplication app = new ProjectApplication(
                null,
                dto.getCompetitionId(),
                dto.getAuthorId(),
                dto.getProjectName(),
                dto.getRequestedAmount(),
                dto.getStatus() != null ? dto.getStatus() : "SUBMITTED",
                LocalDateTime.now()
        );
        return applicationRepository.save(app);
    }

    public ProjectApplication update(Long id, ProjectApplicationDto dto) {
        ProjectApplication app = getById(id);
        if (dto.getCompetitionId() != null) {
            if (!competitionRepository.existsById(dto.getCompetitionId())) {
                throw new IllegalArgumentException("Конкурс с id " + dto.getCompetitionId() + " не найден");
            }
            app.setCompetitionId(dto.getCompetitionId());
        }
        if (dto.getAuthorId() != null) {
            if (!userRepository.existsById(dto.getAuthorId())) {
                throw new IllegalArgumentException("Автор с id " + dto.getAuthorId() + " не найден");
            }
            app.setAuthorId(dto.getAuthorId());
        }
        if (dto.getProjectName() != null) app.setProjectName(dto.getProjectName());
        if (dto.getRequestedAmount() != null) app.setRequestedAmount(dto.getRequestedAmount());
        if (dto.getStatus() != null) app.setStatus(dto.getStatus());
        return applicationRepository.save(app);
    }

    public void delete(Long id) {
        if (!applicationRepository.existsById(id)) {
            throw new NoSuchElementException("Заявка с id " + id + " не найдена");
        }
        reviewRepository.findAll().removeIf(r -> id.equals(r.getApplicationId()));
        decisionRepository.findAll().removeIf(d -> id.equals(d.getApplicationId()));
        applicationRepository.deleteById(id);
    }
}