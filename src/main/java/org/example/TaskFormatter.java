package org.example;

public class TaskFormatter {
    public String format(Task task) {
        String description = task.getDescription().isEmpty() ? "" : " | " + task.getDescription();
        return task.getId() + " | " + task.getStatus() + " | " + task.getTitle() + description;
    }
}

