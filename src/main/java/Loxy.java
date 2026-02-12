import java.util.Scanner;
import java.util.ArrayList;

public class Loxy {
    private static final String SEPARATOR = "____________________________________________________________";

    private static final ArrayList<Task> taskList = new ArrayList<>();

    public static void main(String[] args) {
        printWelcomeLogo();
        printWelcomeMessage();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String userInput = scanner.nextLine().trim();
            if ("bye".equalsIgnoreCase(userInput)) {
                break;
            }
            // List all tasks
            else if ("list".equalsIgnoreCase(userInput)) {
                printTaskList();
            }
            // Mark task as done
            else if (userInput.startsWith("mark ")) {
                handleMarkTask(userInput, true);
            }
            // Mark task as not done
            else if (userInput.startsWith("unmark ")) {
                handleMarkTask(userInput, false);
            }
            // Add new task (non-empty input)
            else if (!userInput.isEmpty()) {
                addTask(userInput);
            }
        }

        printGoodbyeMessage();
        scanner.close();
    }

    private static void printWelcomeLogo() {
        String logo = """
                | |     / __ \\\\ \\\\  // \\\\  //
                | |    | |  | |  \\\\//   \\\\//
                | |___ | |__| |  //\\\\    ||
                |______|\\\\___/  //  \\\\   ||""";
        System.out.println("Hello from\n" + logo);
    }

    private static void printWelcomeMessage() {
        System.out.println(SEPARATOR);
        System.out.println(" Hello! I'm Loxy");
        System.out.println(" What can I do for you?");
        System.out.println(SEPARATOR);
    }

    private static void addTask(String taskContent) {
        taskList.add(new Task(taskContent));
        System.out.println(SEPARATOR);
        System.out.println(" added: " + taskContent);
        System.out.println(SEPARATOR);
    }

    private static void printTaskList() {
        System.out.println(SEPARATOR);
        if (taskList.isEmpty()) {
            System.out.println(" No tasks added yet!");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < taskList.size(); i++) {
                Task task = taskList.get(i);
                System.out.println(" " + (i + 1) + "." + task);
            }
        }
        System.out.println(SEPARATOR);
    }

    private static void handleMarkTask(String userInput, boolean isMarkDone) {
        try {
            int substringStart = isMarkDone ? 5 : 7;
            String numberPart = userInput.substring(substringStart).trim();
            int taskNumber = Integer.parseInt(numberPart);
            int taskIndex = taskNumber - 1;

            if (taskIndex >= 0 && taskIndex < taskList.size()) {
                Task targetTask = taskList.get(taskIndex);
                if (isMarkDone) {
                    targetTask.markAsDone();
                    System.out.println(SEPARATOR);
                    System.out.println(" Nice! I've marked this task as done:");
                } else {
                    targetTask.unmarkAsDone();
                    System.out.println(SEPARATOR);
                    System.out.println(" OK, I've marked this task as not done yet:");
                }
                System.out.println("   " + targetTask);
                System.out.println(SEPARATOR);
            } else {
                printErrorMessage("Oops! This task number does not exist.");
            }
        } catch (StringIndexOutOfBoundsException e) {
            printErrorMessage("Oops! Please specify a task number after 'mark'/'unmark'.");
        }
    }

    private static void printErrorMessage(String message) {
        System.out.println(SEPARATOR);
        System.out.println(" " + message);
        System.out.println(SEPARATOR);
    }

    private static void printGoodbyeMessage() {
        System.out.println(SEPARATOR);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(SEPARATOR);
    }

    private static class Task {
        private final String content;
        private boolean isDone;

        public Task(String content) {
            this.content = content;
            this.isDone = false;
        }

        public void markAsDone() {
            this.isDone = true;
        }

        public void unmarkAsDone() {
            this.isDone = false;
        }

        @Override
        public String toString() {
            return "[" + (isDone ? "X" : " ") + "] " + content;
        }
    }
}