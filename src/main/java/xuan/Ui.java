package xuan;

import java.util.List;
import java.util.Scanner;

public class Ui {
    private static final String SEPARATOR = "____________________________________________________________";
    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        String logo = """
                | |     / __ \\\\ \\\\  // \\\\  //
                | |    | |  | |  \\\\//   \\\\//
                | |___ | |__| |  //\\\\    ||
                |______|\\\\___/  //  \\\\   ||""";
        System.out.println("Hello from\n" + logo);
        showLine();
        System.out.println(" Hello! I'm Loxy");
        System.out.println(" What can I do for you?");
        showLine();
    }

    public void showLine() {
        System.out.println(SEPARATOR);
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void showError(String message) {
        showLine();
        System.out.println(" " + message);
        showLine();
    }

    public void showLoadingError() {
        showError("Error loading tasks! Creating empty list.");
    }

    public void showTaskAdded(Task task, int taskCount) {
        showLine();
        System.out.println(" Got it. Added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks.");
        showLine();
    }

    public void showTaskDeleted(Task task, int taskCount) {
        showLine();
        System.out.println(" Removed this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks.");
        showLine();
    }

    public void showTaskMarked(Task task, boolean isDone) {
        showLine();
        if (isDone) {
            System.out.println(" Marked as done:");
        } else {
            System.out.println(" Marked as not done:");
        }
        System.out.println("   " + task);
        showLine();
    }

    public void showTaskList(TaskList tasks) {
        showLine();
        if (tasks.isEmpty()) {
            System.out.println(" No tasks yet!");
        } else {
            System.out.println(" Your tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i));
            }
        }
        showLine();
    }

    public void showGoodbye() {
        showLine();
        System.out.println(" Bye! See you soon.");
        showLine();
    }
}