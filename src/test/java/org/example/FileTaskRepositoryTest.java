package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileTaskRepositoryTest {
    @TempDir
    Path tempDir;

    @Test
    void savesAndLoadsTasks() {
        Path file = tempDir.resolve("tasks.db");
        FileTaskRepository repository = new FileTaskRepository(file);
        Task task = Task.createNew("Persisted", "Should load");

        repository.save(task);

        FileTaskRepository loadedRepository = new FileTaskRepository(file);
        Task loaded = loadedRepository.findById(task.getId()).orElseThrow();
        assertEquals(task.getTitle(), loaded.getTitle());
        assertEquals(task.getStatus(), loaded.getStatus());
    }

    @Test
    void deletesTasks() {
        Path file = tempDir.resolve("tasks.db");
        FileTaskRepository repository = new FileTaskRepository(file);
        Task task = Task.createNew("Delete", "Remove");
        repository.save(task);

        repository.delete(task.getId());

        assertTrue(repository.findAll().isEmpty());
    }
}


