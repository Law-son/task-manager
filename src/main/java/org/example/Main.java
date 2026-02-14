package org.example;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String DATA_FILE = "data/tasks.db";

    public static void main(String[] args) {
        TaskRepository repository = createRepository();
        TaskService service = new TaskService(repository);
        TaskFormatter formatter = new TaskFormatter();
        Scanner scanner = new Scanner(System.in);
        printWelcome();

        while (true) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                break;
            }
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s+", 2);
            String command = parts[0].toLowerCase();
            String arguments = parts.length > 1 ? parts[1].trim() : "";

            switch (command) {
                case "help":
                    printHelp();
                    break;
                case "add":
                    handleAdd(service, arguments);
                    break;
                case "list":
                    handleList(service, formatter, arguments);
                    break;
                case "complete":
                    handleComplete(service, arguments);
                    break;
                case "delete":
                    handleDelete(service, arguments);
                    break;
                case "status":
                    handleStatus(service);
                    break;
                case "exit":
                case "quit":
                    System.out.println("Goodbye.");
                    return;
                default:
                    System.out.println("Unknown command. Type 'help' for options.");
                    break;
            }
        }
    }

    private static void printWelcome() {
        System.out.println("Task Manager");
        System.out.println("Type 'help' for a list of commands.");
    }

    private static void printHelp() {
        System.out.println("Commands:");
        System.out.println("  add <title> | <description>   Add a new task");
        System.out.println("  list [all|open|done]          List tasks");
        System.out.println("  complete <id>                 Mark a task as done");
        System.out.println("  delete <id>                   Delete a task");
        System.out.println("  status                        Show task counts");
        System.out.println("  help                          Show this help");
        System.out.println("  exit                          Quit the app");
    }

    private static void handleAdd(TaskService service, String arguments) {
        if (arguments.isEmpty()) {
            System.out.println("Usage: add <title> | <description>");
            return;
        }
        String[] parts = arguments.split("\\|", 2);
        String title = parts[0].trim();
        String description = parts.length > 1 ? parts[1].trim() : "";
        Task task = service.addTask(title, description);
        LOGGER.info("Added task " + task.getId());
        System.out.println("Added: " + task.getId() + " | " + task.getTitle());
    }

    private static void handleList(TaskService service, TaskFormatter formatter, String arguments) {
        Optional<TaskStatus> filter = parseStatusFilter(arguments);
        if (arguments.length() > 0 && filter.isEmpty()) {
            System.out.println("Usage: list [all|open|done]");
            return;
        }
        List<Task> tasks = service.listTasks(filter);
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        for (Task task : tasks) {
            System.out.println(formatter.format(task));
        }
    }

    private static void handleComplete(TaskService service, String arguments) {
        UUID id = parseId(arguments);
        if (id == null) {
            System.out.println("Usage: complete <id>");
            return;
        }
        boolean success = service.completeTask(id);
        if (success) {
            LOGGER.info("Completed task " + id);
            System.out.println("Completed: " + id);
        } else {
            System.out.println("Task not found: " + id);
        }
    }

    private static void handleDelete(TaskService service, String arguments) {
        UUID id = parseId(arguments);
        if (id == null) {
            System.out.println("Usage: delete <id>");
            return;
        }
        boolean success = service.deleteTask(id);
        if (success) {
            LOGGER.info("Deleted task " + id);
            System.out.println("Deleted: " + id);
        } else {
            System.out.println("Task not found: " + id);
        }
    }

    private static void handleStatus(TaskService service) {
        TaskSummary summary = service.getSummary();
        System.out.println("Total: " + summary.total() + " | Open: " + summary.open() + " | Done: " + summary.done());
    }

    private static TaskRepository createRepository() {
        try {
            return new FileTaskRepository(Paths.get(DATA_FILE));
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Falling back to in-memory storage", e);
            return new InMemoryTaskRepository();
        }
    }

    private static Optional<TaskStatus> parseStatusFilter(String arguments) {
        if (arguments == null || arguments.isEmpty() || arguments.equalsIgnoreCase("all")) {
            return Optional.empty();
        }
        if (arguments.equalsIgnoreCase("open")) {
            return Optional.of(TaskStatus.OPEN);
        }
        if (arguments.equalsIgnoreCase("done")) {
            return Optional.of(TaskStatus.DONE);
        }
        return Optional.empty();
    }

    private static UUID parseId(String arguments) {
        if (arguments == null || arguments.isEmpty()) {
            return null;
        }
        try {
            return UUID.fromString(arguments.trim());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}