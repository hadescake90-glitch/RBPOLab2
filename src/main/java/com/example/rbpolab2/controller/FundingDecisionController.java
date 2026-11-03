package com.example.rbpolab2.controller;

import com.example.rbpolab2.dto.FundingDecisionDto;
import com.example.rbpolab2.model.FundingDecision;
import com.example.rbpolab2.service.FundingDecisionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funding-decisions")
public class FundingDecisionController {

    private final FundingDecisionService decisionService;

    public FundingDecisionController(FundingDecisionService decisionService) {
        this.decisionService = decisionService;
    }

    @GetMapping
    public List<FundingDecision> getAll() {
        return decisionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FundingDecision> getById(@PathVariable Long id) {
        return decisionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FundingDecision> create(@RequestBody FundingDecisionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(decisionService.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FundingDecision> update(@PathVariable Long id, @RequestBody FundingDecisionDto dto) {
        return decisionService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (decisionService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}