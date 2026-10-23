package com.example.rbpolab2.controller;

import com.example.rbpolab2.dto.ProjectApplicationDto;
import com.example.rbpolab2.model.ProjectApplication;
import com.example.rbpolab2.service.ProjectApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-applications")
public class ProjectApplicationController {

    private final ProjectApplicationService applicationService;

    public ProjectApplicationController(ProjectApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public List<ProjectApplication> getAll() {
        return applicationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectApplication> getById(@PathVariable Long id) {
        return applicationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProjectApplication> create(@RequestBody ProjectApplicationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationService.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectApplication> update(@PathVariable Long id, @RequestBody ProjectApplicationDto dto) {
        return applicationService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (applicationService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Бизнес-операция 1: Подача заявки с проверками
    @PostMapping("/submit")
    public ResponseEntity<ProjectApplication> submit(@RequestBody ProjectApplicationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationService.submitApplication(dto));
    }

    // Бизнес-операция 4: Выплата средств
    @PostMapping("/{id}/disburse")
    public ResponseEntity<ProjectApplication> disburse(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.disburseGrant(id));
    }

    // Бизнес-операция 5: Отзыв заявки
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<ProjectApplication> withdraw(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.withdrawApplication(id));
    }
}