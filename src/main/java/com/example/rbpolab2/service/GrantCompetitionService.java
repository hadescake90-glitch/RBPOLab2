package com.example.rbpolab2.service;

import com.example.rbpolab2.dto.GrantCompetitionDto;
import com.example.rbpolab2.model.GrantCompetition;
import com.example.rbpolab2.repository.ExpertReviewRepository;
import com.example.rbpolab2.repository.FundingDecisionRepository;
import com.example.rbpolab2.repository.GrantCompetitionRepository;
import com.example.rbpolab2.repository.ProjectApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GrantCompetitionService {

    private final GrantCompetitionRepository competitionRepository;
    private final ProjectApplicationRepository applicationRepository;
    private final ExpertReviewRepository reviewRepository;
    private final FundingDecisionRepository decisionRepository;

    public GrantCompetitionService(GrantCompetitionRepository competitionRepository,
                                   ProjectApplicationRepository applicationRepository,
                                   ExpertReviewRepository reviewRepository,
                                   FundingDecisionRepository decisionRepository) {
        this.competitionRepository = competitionRepository;
        this.applicationRepository = applicationRepository;
        this.reviewRepository = reviewRepository;
        this.decisionRepository = decisionRepository;
    }

    public List<GrantCompetition> getAll() {
        return competitionRepository.findAll();
    }

    public GrantCompetition getById(Long id) {
        return competitionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Конкурс с id " + id + " не найден"));
    }

    public GrantCompetition create(GrantCompetitionDto dto) {
        GrantCompetition competition = new GrantCompetition(
                null,
                dto.getTitle(),
                dto.getBudget(),
                dto.getSubmissionDeadline(),
                dto.getEvaluationDeadline(),
                dto.getStatus() != null ? dto.getStatus() : "OPEN",
                LocalDateTime.now()
        );
        return competitionRepository.save(competition);
    }

    public GrantCompetition update(Long id, GrantCompetitionDto dto) {
        GrantCompetition competition = getById(id);
        if (dto.getTitle() != null) competition.setTitle(dto.getTitle());
        if (dto.getBudget() != null) competition.setBudget(dto.getBudget());
        if (dto.getSubmissionDeadline() != null) competition.setSubmissionDeadline(dto.getSubmissionDeadline());
        if (dto.getEvaluationDeadline() != null) competition.setEvaluationDeadline(dto.getEvaluationDeadline());
        if (dto.getStatus() != null) competition.setStatus(dto.getStatus());
        return competitionRepository.save(competition);
    }

    public void delete(Long id) {
        if (!competitionRepository.existsById(id)) {
            throw new NoSuchElementException("Конкурс с id " + id + " не найден");
        }
        // Каскадно удаляем все заявки этого конкурса
        applicationRepository.findAll().stream()
                .filter(app -> id.equals(app.getCompetitionId()))
                .forEach(app -> {
                    reviewRepository.findAll().removeIf(r -> app.getId().equals(r.getApplicationId()));
                    decisionRepository.findAll().removeIf(d -> app.getId().equals(d.getApplicationId()));
                    applicationRepository.deleteById(app.getId());
                });
        competitionRepository.deleteById(id);
    }
}