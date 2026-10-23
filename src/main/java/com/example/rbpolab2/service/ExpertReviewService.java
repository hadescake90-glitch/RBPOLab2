package com.example.rbpolab2.service;

import com.example.rbpolab2.dto.ExpertReviewDto;
import com.example.rbpolab2.model.ExpertReview;
import com.example.rbpolab2.model.ProjectApplication;
import com.example.rbpolab2.model.User;
import com.example.rbpolab2.repository.ExpertReviewRepository;
import com.example.rbpolab2.repository.ProjectApplicationRepository;
import com.example.rbpolab2.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public List<ExpertReview> findAll() {
        return reviewRepository.findAll();
    }

    public Optional<ExpertReview> findById(Long id) {
        return reviewRepository.findById(id);
    }

    @Transactional
    public ExpertReview create(ExpertReviewDto dto) {
        ExpertReview review = new ExpertReview();
        review.setApplicationId(dto.getApplicationId());
        review.setExpertId(dto.getExpertId());
        review.setScore(dto.getScore());
        review.setComment(dto.getComment());
        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    @Transactional
    public Optional<ExpertReview> update(Long id, ExpertReviewDto dto) {
        return reviewRepository.findById(id).map(r -> {
            if (dto.getScore() != null) r.setScore(dto.getScore());
            if (dto.getComment() != null) r.setComment(dto.getComment());
            return reviewRepository.save(r);
        });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (!reviewRepository.existsById(id)) return false;
        reviewRepository.deleteById(id);
        return true;
    }

    @Transactional
    public ExpertReview evaluateApplication(ExpertReviewDto dto) {
        if (dto.getScore() == null || dto.getScore() < 0 || dto.getScore() > 100) {
            throw new IllegalArgumentException("Оценка должна быть в диапазоне от 0 до 100");
        }

        User expert = userRepository.findById(dto.getExpertId())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        if (!"EXPERT".equalsIgnoreCase(expert.getRole())) {
            throw new IllegalStateException("Пользователь не является экспертом");
        }

        ProjectApplication app = applicationRepository.findById(dto.getApplicationId())
                .orElseThrow(() -> new IllegalArgumentException("Заявка не найдена"));

        if (!"SUBMITTED".equalsIgnoreCase(app.getStatus()) && !"UNDER_REVIEW".equalsIgnoreCase(app.getStatus())) {
            throw new IllegalStateException("Заявка не подлежит оценке в текущем статусе");
        }

        if (reviewRepository.existsByApplicationIdAndExpertId(dto.getApplicationId(), dto.getExpertId())) {
            throw new IllegalStateException("Эксперт уже выставил оценку этой заявке");
        }

        app.setStatus("UNDER_REVIEW");
        applicationRepository.save(app);

        ExpertReview review = new ExpertReview();
        review.setApplicationId(dto.getApplicationId());
        review.setExpertId(dto.getExpertId());
        review.setScore(dto.getScore());
        review.setComment(dto.getComment());
        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }
}