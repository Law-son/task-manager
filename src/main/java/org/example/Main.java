package org.example;

import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String DATA_FILE = "data/tasks.db";

    public static void main(String[] args) {
        TaskRepository repository = createRepository();
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

            switch (command) {
                case "help":
                    printHelp();
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
        System.out.println("  help                          Show this help");
        System.out.println("  exit                          Quit the app");
    }

    private static TaskRepository createRepository() {
        try {
            return new FileTaskRepository(Paths.get(DATA_FILE));
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Falling back to in-memory storage", e);
            return new InMemoryTaskRepository();
        }
    }
}