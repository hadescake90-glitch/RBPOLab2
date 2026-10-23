package com.example.rbpolab2.service;

import com.example.rbpolab2.dto.UserDto;
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
public class UserService {

    private final UserRepository userRepository;
    private final ProjectApplicationRepository applicationRepository;
    private final ExpertReviewRepository reviewRepository;

    public UserService(UserRepository userRepository,
                       ProjectApplicationRepository applicationRepository,
                       ExpertReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User create(UserDto dto) {
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Transactional
    public Optional<User> update(Long id, UserDto dto) {
        return userRepository.findById(id).map(user -> {
            if (dto.getFullName() != null) user.setFullName(dto.getFullName());
            if (dto.getEmail() != null) user.setEmail(dto.getEmail());
            if (dto.getRole() != null) user.setRole(dto.getRole());
            return userRepository.save(user);
        });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        reviewRepository.findByExpertId(id).forEach(reviewRepository::delete);
        applicationRepository.findByAuthorId(id).forEach(applicationRepository::delete);
        userRepository.deleteById(id);
        return true;
    }
}