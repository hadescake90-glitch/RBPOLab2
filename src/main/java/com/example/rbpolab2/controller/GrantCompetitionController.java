package com.example.rbpolab2.controller;

import com.example.rbpolab2.dto.GrantCompetitionDto;
import com.example.rbpolab2.model.GrantCompetition;
import com.example.rbpolab2.service.GrantCompetitionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grant-competitions")
public class GrantCompetitionController {

    private final GrantCompetitionService service;

    public GrantCompetitionController(GrantCompetitionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<GrantCompetition>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrantCompetition> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<GrantCompetition> create(@RequestBody GrantCompetitionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GrantCompetition> update(@PathVariable Long id, @RequestBody GrantCompetitionDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}