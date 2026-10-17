package com.example.rbpolab2.service;

import com.example.rbpolab2.dto.ExpertReviewDto;
import com.example.rbpolab2.model.ExpertReview;
import com.example.rbpolab2.repository.ExpertReviewRepository;
import com.example.rbpolab2.repository.ProjectApplicationRepository;
import com.example.rbpolab2.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ExpertReviewService {

    private final ExpertReviewRepository reviewRepository;
    private final ProjectApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public ExpertReviewService(ExpertReviewRepository reviewRepository,
                               ProjectApplicationRepository applicationRepository,
                               UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    public List<ExpertReview> getAll() {
        return reviewRepository.findAll();
    }

    public ExpertReview getById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Экспертная оценка с id " + id + " не найдена"));
    }

    public ExpertReview create(ExpertReviewDto dto) {
        if (dto.getApplicationId() == null || !applicationRepository.existsById(dto.getApplicationId())) {
            throw new IllegalArgumentException("Заявка с id " + dto.getApplicationId() + " не найдена");
        }
        if (dto.getExpertId() == null || !userRepository.existsById(dto.getExpertId())) {
            throw new IllegalArgumentException("Эксперт с id " + dto.getExpertId() + " не найден");
        }
        ExpertReview review = new ExpertReview(
                null,
                dto.getApplicationId(),
                dto.getExpertId(),
                dto.getScore(),
                dto.getComment(),
                LocalDateTime.now()
        );
        return reviewRepository.save(review);
    }

    public ExpertReview update(Long id, ExpertReviewDto dto) {
        ExpertReview review = getById(id);
        if (dto.getScore() != null) review.setScore(dto.getScore());
        if (dto.getComment() != null) review.setComment(dto.getComment());
        return reviewRepository.save(review);
    }

    public void delete(Long id) {
        if (!reviewRepository.deleteById(id)) {
            throw new NoSuchElementException("Экспертная оценка с id " + id + " не найдена");
        }
    }
}