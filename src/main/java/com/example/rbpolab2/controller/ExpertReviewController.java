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
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpertReview> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ExpertReview> create(@RequestBody ExpertReviewDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExpertReview> update(@PathVariable Long id, @RequestBody ExpertReviewDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}