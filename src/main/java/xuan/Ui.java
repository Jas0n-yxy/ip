package xuan;

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
        showError("Error loading tasks from file! Creating empty task list.");
    }

    public void showTaskAdded(Task task, int taskCount) {
        showLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        showLine();
    }

    public void showTaskDeleted(Task task, int taskCount) {
        showLine();
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        showLine();
    }

    public void showTaskMarked(Task task, boolean isDone) {
        showLine();
        if (isDone) {
            System.out.println(" Nice! I've marked this task as done:");
        } else {
            System.out.println(" OK, I've marked this task as not done yet:");
        }
        System.out.println("   " + task);
        showLine();
    }

    public void showTaskList(TaskList tasks) {
        showLine();
        if (tasks.isEmpty()) {
            System.out.println(" No tasks added yet!");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i));
            }
        }
        showLine();
    }

    public void showGoodbye() {
        showLine();
        System.out.println(" Bye. Hope to see you again soon!");
        showLine();
    }
}