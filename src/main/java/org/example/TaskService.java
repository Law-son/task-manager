package org.example;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task addTask(String title, String description) {
        Task task = Task.createNew(title, description);
        repository.save(task);
        return task;
    }

    public List<Task> listTasks(Optional<TaskStatus> statusFilter) {
        List<Task> tasks = repository.findAll();
        if (statusFilter.isEmpty()) {
            return tasks;
        }
        TaskStatus status = statusFilter.get();
        return tasks.stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
    }

    public Optional<Task> getTask(UUID id) {
        return repository.findById(id);
    }

    public boolean completeTask(UUID id) {
        Optional<Task> taskOptional = repository.findById(id);
        if (taskOptional.isEmpty()) {
            return false;
        }
        Task task = taskOptional.get();
        task.markDone();
        repository.save(task);
        return true;
    }

    public boolean deleteTask(UUID id) {
        Optional<Task> taskOptional = repository.findById(id);
        if (taskOptional.isEmpty()) {
            return false;
        }
        repository.delete(id);
        return true;
    }

    public TaskSummary getSummary() {
        List<Task> tasks = repository.findAll();
        int total = tasks.size();
        int done = (int) tasks.stream().filter(task -> task.getStatus() == TaskStatus.DONE).count();
        int open = total - done;
        return new TaskSummary(total, open, done);
    }
}

