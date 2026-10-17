package com.example.rbpolab2.repository;

import com.example.rbpolab2.model.FundingDecision;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class FundingDecisionRepository {
    private final Map<Long, FundingDecision> storage = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(0);

    public List<FundingDecision> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Optional<FundingDecision> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public FundingDecision save(FundingDecision decision) {
        if (decision.getId() == null) {
            decision.setId(idSequence.incrementAndGet());
        }
        storage.put(decision.getId(), decision);
        return decision;
    }

    public boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }
}