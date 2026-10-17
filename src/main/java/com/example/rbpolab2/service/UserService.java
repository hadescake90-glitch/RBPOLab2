package com.example.rbpolab2.service;

import com.example.rbpolab2.dto.UserDto;
import com.example.rbpolab2.model.User;
import com.example.rbpolab2.repository.ExpertReviewRepository;
import com.example.rbpolab2.repository.FundingDecisionRepository;
import com.example.rbpolab2.repository.ProjectApplicationRepository;
import com.example.rbpolab2.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProjectApplicationRepository applicationRepository;
    private final ExpertReviewRepository reviewRepository;
    private final FundingDecisionRepository decisionRepository;

    public UserService(UserRepository userRepository,
                       ProjectApplicationRepository applicationRepository,
                       ExpertReviewRepository reviewRepository,
                       FundingDecisionRepository decisionRepository) {
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
        this.reviewRepository = reviewRepository;
        this.decisionRepository = decisionRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Пользователь с id " + id + " не найден"));
    }

    public User create(UserDto dto) {
        User user = new User(
                null,
                dto.getFullName(),
                dto.getEmail(),
                dto.getRole() != null ? dto.getRole() : "APPLICANT",
                LocalDateTime.now()
        );
        return userRepository.save(user);
    }

    public User update(Long id, UserDto dto) {
        User user = getById(id);
        if (dto.getFullName() != null) user.setFullName(dto.getFullName());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getRole() != null) user.setRole(dto.getRole());
        return userRepository.save(user);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("Пользователь с id " + id + " не найден");
        }
        // Каскадно удаляем все заявки этого автора и всё, что к ним привязано
        applicationRepository.findAll().stream()
                .filter(app -> id.equals(app.getAuthorId()))
                .forEach(app -> {
                    reviewRepository.findAll().removeIf(r -> app.getId().equals(r.getApplicationId()));
                    decisionRepository.findAll().removeIf(d -> app.getId().equals(d.getApplicationId()));
                    applicationRepository.deleteById(app.getId());
                });
        // Удаляем оценки, где данный пользователь выступал экспертом
        reviewRepository.findAll().removeIf(r -> id.equals(r.getExpertId()));
        userRepository.deleteById(id);
    }
}