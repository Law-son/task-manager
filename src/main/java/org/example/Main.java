package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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
}