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

    private final ProjectApplicationService service;

    public ProjectApplicationController(ProjectApplicationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProjectApplication>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectApplication> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ProjectApplication> create(@RequestBody ProjectApplicationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectApplication> update(@PathVariable Long id, @RequestBody ProjectApplicationDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}