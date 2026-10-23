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

    private final GrantCompetitionService competitionService;

    public GrantCompetitionController(GrantCompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @GetMapping
    public List<GrantCompetition> getAll() {
        return competitionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrantCompetition> getById(@PathVariable Long id) {
        return competitionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GrantCompetition> create(@RequestBody GrantCompetitionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(competitionService.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GrantCompetition> update(@PathVariable Long id, @RequestBody GrantCompetitionDto dto) {
        return competitionService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (competitionService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Бизнес-операция 3: Завершение конкурса
    @PostMapping("/{id}/finalize")
    public ResponseEntity<GrantCompetition> finalizeCompetition(@PathVariable Long id) {
        return ResponseEntity.ok(competitionService.finalizeCompetition(id));
    }
}