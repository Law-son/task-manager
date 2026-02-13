package org.example;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Task {
    private final UUID id;
    private final String title;
    private final String description;
    private TaskStatus status;
    private final Instant createdAt;
    private Instant completedAt;

    public Task(UUID id, String title, String description, TaskStatus status, Instant createdAt, Instant completedAt) {
        this.id = Objects.requireNonNull(id, "id");
        this.title = validateTitle(title);
        this.description = description == null ? "" : description.trim();
        this.status = Objects.requireNonNull(status, "status");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt");
        this.completedAt = completedAt;
    }

    public static Task createNew(String title, String description) {
        return new Task(UUID.randomUUID(), title, description, TaskStatus.OPEN, Instant.now(), null);
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public void markDone() {
        if (status == TaskStatus.DONE) {
            return;
        }
        status = TaskStatus.DONE;
        completedAt = Instant.now();
    }

    private static String validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("title is required");
        }
        return title.trim();
    }
}

