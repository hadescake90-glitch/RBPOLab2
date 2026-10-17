package com.example.rbpolab2.repository;

import com.example.rbpolab2.model.ExpertReview;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ExpertReviewRepository {
    private final Map<Long, ExpertReview> storage = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(0);

    public List<ExpertReview> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Optional<ExpertReview> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public ExpertReview save(ExpertReview review) {
        if (review.getId() == null) {
            review.setId(idSequence.incrementAndGet());
        }
        storage.put(review.getId(), review);
        return review;
    }

    public boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }
}