package org.example;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {
    List<Task> findAll();

    Optional<Task> findById(UUID id);

    void save(Task task);

    void delete(UUID id);
}

