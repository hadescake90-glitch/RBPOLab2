package com.example.rbpolab2.controller;

import com.example.rbpolab2.dto.ExpertReviewDto;
import com.example.rbpolab2.model.ExpertReview;
import com.example.rbpolab2.service.ExpertReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expert-reviews")
public class ExpertReviewController {

    private final ExpertReviewService service;

    public ExpertReviewController(ExpertReviewService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ExpertReview>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpertReview> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ExpertReview> create(@RequestBody ExpertReviewDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExpertReview> update(@PathVariable Long id, @RequestBody ExpertReviewDto dto) {
        return service.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Бизнес-операция 2: Проведение оценки экспертом
    @PostMapping("/evaluate")
    public ResponseEntity<ExpertReview> evaluate(@RequestBody ExpertReviewDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.evaluateApplication(dto));
    }
}