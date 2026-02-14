package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {
    @Test
    void addTaskStoresAndLists() {
        TaskService service = new TaskService(new InMemoryTaskRepository());
        Task task = service.addTask("Write tests", "Cover add/list");

        List<Task> tasks = service.listTasks(Optional.empty());

        assertEquals(1, tasks.size());
        assertEquals(task.getId(), tasks.get(0).getId());
        assertEquals(TaskStatus.OPEN, tasks.get(0).getStatus());
    }

    @Test
    void completeTaskUpdatesStatus() {
        TaskService service = new TaskService(new InMemoryTaskRepository());
        Task task = service.addTask("Finish story", "");

        boolean completed = service.completeTask(task.getId());

        assertTrue(completed);
        Task updated = service.getTask(task.getId()).orElseThrow();
        assertEquals(TaskStatus.DONE, updated.getStatus());
        assertNotNull(updated.getCompletedAt());
    }

    @Test
    void listTasksFiltersByStatus() {
        TaskService service = new TaskService(new InMemoryTaskRepository());
        Task openTask = service.addTask("Open", "");
        Task doneTask = service.addTask("Done", "");
        service.completeTask(doneTask.getId());

        List<Task> openTasks = service.listTasks(Optional.of(TaskStatus.OPEN));

        assertEquals(1, openTasks.size());
        assertEquals(openTask.getId(), openTasks.get(0).getId());
    }

    @Test
    void deleteTaskRemovesItem() {
        TaskService service = new TaskService(new InMemoryTaskRepository());
        Task task = service.addTask("Delete me", "");

        boolean deleted = service.deleteTask(task.getId());

        assertTrue(deleted);
        assertTrue(service.listTasks(Optional.empty()).isEmpty());
    }
}


