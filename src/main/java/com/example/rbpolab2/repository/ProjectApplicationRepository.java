package com.example.rbpolab2.repository;

import com.example.rbpolab2.model.ProjectApplication;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProjectApplicationRepository {
    private final Map<Long, ProjectApplication> storage = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(0);

    public List<ProjectApplication> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Optional<ProjectApplication> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public ProjectApplication save(ProjectApplication app) {
        if (app.getId() == null) {
            app.setId(idSequence.incrementAndGet());
        }
        storage.put(app.getId(), app);
        return app;
    }

    public boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }

    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
}