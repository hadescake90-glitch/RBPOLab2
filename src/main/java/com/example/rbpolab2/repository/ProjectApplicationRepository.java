package com.example.rbpolab2.repository;

import com.example.rbpolab2.model.ProjectApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectApplicationRepository extends JpaRepository<ProjectApplication, Long> {
    List<ProjectApplication> findByCompetitionId(Long competitionId);
    List<ProjectApplication> findByAuthorId(Long authorId);
}