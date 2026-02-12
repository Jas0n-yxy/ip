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
            else if ("list".equalsIgnoreCase(userInput)) {
                printTaskList();
            }
            else if (userInput.startsWith("mark ")) {
                handleMarkTask(userInput, true);
            }
            else if (userInput.startsWith("unmark ")) {
                handleMarkTask(userInput, false);
            }
            else if (!userInput.isEmpty()) {
                addTask(userInput);
            }
        }

        printGoodbyeMessage();
        scanner.close();
    }

 private static void printWelcomeLogo() {
        String LOgo =
                " | |     / __ \\\\ \\\\  // \\\\  //\n"
                        + " | |    | |  | |  \\\\//   \\\\// \n"
                        + " | |___ | |__| |  //\\\\    ||  \n"
                        + " |______|\\\\___/  //  \\\\   ||  \n";
        System.out.println("Hello from\n" + LOgo);
    }

    private static void printWelcomeMessage() {
        System.out.println(SEPARATOR);


        System.out.println(" Hello! I'm Loxy");
        System.out.println(" What can I do for you?");
        System.out.println(SEPARATOR);
    }

    private static void addTask(String tC) {
        taskList.add(new Task(tC));
        System.out.println(SEPARATOR);
        System.out.println(" added: " + tC);
        System.out.println(SEPARATOR);
    }

    private static void printTaskList() {
        System.out.println(SEPARATOR);
        if (taskList.isEmpty()) {
            System.out.println(" No tasks added yet!");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int idx = 0; idx < taskList.size(); idx++) {
                Task task = taskList.get(idx);
                System.out.println(" " + (idx + 1) + "." + task);
            }
        }
        System.out.println(SEPARATOR);
    }

    private static void handleMarkTask(String userInput, boolean isd) {
        try {
            String numberPart = userInput.substring(isd ? 5 : 7).trim();
            int taskNumber = Integer.parseInt(numberPart);
            int taskIndex = taskNumber - 1;

            if (taskIndex >= 0 && taskIndex < taskList.size()) {
                Task targetTask = taskList.get(taskIndex);
                if (isd) {
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
        } catch (StringIndexOutOfBoundsException ex) {
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

    static class Task {
        private final String cont;
        private boolean isDone;

        public Task(String c) {
            this.cont = c;
            this.isDone = false;
        }

        public void markAsDone() {
            this.isDone = true;
        }

        public void unmarkAsDone() {
            this.isDone = false;
        }

        public String toString() {
            return "["+(isDone ? "X" : " ")+"] "+cont;
        }
    }
}