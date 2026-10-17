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

    private final FundingDecisionService service;

    public FundingDecisionController(FundingDecisionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<FundingDecision>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FundingDecision> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<FundingDecision> create(@RequestBody FundingDecisionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FundingDecision> update(@PathVariable Long id, @RequestBody FundingDecisionDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}