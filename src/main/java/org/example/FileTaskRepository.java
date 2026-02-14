package org.example;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FileTaskRepository implements TaskRepository {
    private static final String DELIMITER = "|";
    private final Path filePath;
    private final Map<UUID, Task> tasks = new LinkedHashMap<>();

    public FileTaskRepository(Path filePath) {
        this.filePath = filePath;
        loadFromFile();
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public void save(Task task) {
        tasks.put(task.getId(), task);
        writeAll();
    }

    @Override
    public void delete(UUID id) {
        tasks.remove(id);
        writeAll();
    }

    private void loadFromFile() {
        if (!Files.exists(filePath)) {
            return;
        }
        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                Task task = parseLine(line);
                tasks.put(task.getId(), task);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load tasks from file", e);
        }
    }

    private void writeAll() {
        List<String> lines = new ArrayList<>();
        for (Task task : tasks.values()) {
            lines.add(formatLine(task));
        }
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Files.write(filePath, lines);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to save tasks to file", e);
        }
    }

    private Task parseLine(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 6) {
            throw new IllegalStateException("Invalid task record: " + line);
        }
        UUID id = UUID.fromString(parts[0]);
        String title = decode(parts[1]);
        String description = decode(parts[2]);
        TaskStatus status = TaskStatus.valueOf(parts[3]);
        Instant createdAt = Instant.parse(parts[4]);
        Instant completedAt = parts[5].isEmpty() ? null : Instant.parse(parts[5]);
        return new Task(id, title, description, status, createdAt, completedAt);
    }

    private String formatLine(Task task) {
        String completedAt = task.getCompletedAt() == null ? "" : task.getCompletedAt().toString();
        return String.join(DELIMITER,
                task.getId().toString(),
                encode(task.getTitle()),
                encode(task.getDescription()),
                task.getStatus().name(),
                task.getCreatedAt().toString(),
                completedAt);
    }

    private String encode(String value) {
        return URLEncoder.encode(value == null ? "" : value, StandardCharsets.UTF_8);
    }

    private String decode(String value) {
        return URLDecoder.decode(value == null ? "" : value, StandardCharsets.UTF_8);
    }
}

