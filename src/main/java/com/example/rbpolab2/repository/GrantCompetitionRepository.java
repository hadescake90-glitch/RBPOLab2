package com.example.rbpolab2.repository;

import com.example.rbpolab2.model.GrantCompetition;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class GrantCompetitionRepository {
    private final Map<Long, GrantCompetition> storage = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(0);

    public List<GrantCompetition> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Optional<GrantCompetition> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public GrantCompetition save(GrantCompetition comp) {
        if (comp.getId() == null) {
            comp.setId(idSequence.incrementAndGet());
        }
        storage.put(comp.getId(), comp);
        return comp;
    }

    public boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }

    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
}