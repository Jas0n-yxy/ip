import java.util.Scanner;
import java.util.ArrayList;

public class Loxy {
    public static void main(String[] args) {
        String logo =
                " | |     / __ \\\\ \\\\  // \\\\  //\n"
                        + " | |    | |  | |  \\\\//   \\\\// \n"
                        + " | |___ | |__| |  //\\\\    ||  \n"
                        + " |______|\\\\___/  //  \\\\   ||  \n";
        System.out.println("Hello from\n" + logo);

        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Loxy");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<>();

        while (true) {
            String userInput = scanner.nextLine();

            if ("bye".equals(userInput)) {
                break;
            }

            if ("list".equals(userInput)) {
                System.out.println("____________________________________________________________");
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskList.size(); i++) {
                    Task task = taskList.get(i);
                    System.out.println(" " + (i + 1) + ".[" + task.getStatusIcon() + "] " + task.description);
                }
                System.out.println("____________________________________________________________");
            }
            else if (userInput.startsWith("mark ")) {
                try {
                    int taskNumber = Integer.parseInt(userInput.substring(5));
                    int taskIndex = taskNumber - 1;

                    if (taskIndex >= 0 && taskIndex < taskList.size()) {
                        Task targetTask = taskList.get(taskIndex);
                        targetTask.markAsDone();
                        System.out.println("____________________________________________________________");
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   [" + targetTask.getStatusIcon() + "] " + targetTask.description);
                        System.out.println("____________________________________________________________");
                    } else {
                        System.out.println("____________________________________________________________");
                        System.out.println(" Oops! This task number does not exist.");
                        System.out.println("____________________________________________________________");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Oops! Please enter a valid number after 'mark'.");
                    System.out.println("____________________________________________________________");
                }
            }
            else if (userInput.startsWith("unmark ")) {
                try {
                    int taskNumber = Integer.parseInt(userInput.substring(7));
                    int taskIndex = taskNumber - 1;

                    if (taskIndex >= 0 && taskIndex < taskList.size()) {
                        Task targetTask = taskList.get(taskIndex);
                        targetTask.unmarkAsDone();
                        System.out.println("____________________________________________________________");
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   [" + targetTask.getStatusIcon() + "] " + targetTask.description);
                        System.out.println("____________________________________________________________");
                    } else {
                        System.out.println("____________________________________________________________");
                        System.out.println(" Oops! This task number does not exist.");
                        System.out.println("____________________________________________________________");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Oops! Please enter a valid number after 'unmark'.");
                    System.out.println("____________________________________________________________");
                }
            }
            else {
                Task newTask = new Task(userInput);
                taskList.add(newTask);
                System.out.println("____________________________________________________________");
                System.out.println(" added: " + userInput);
                System.out.println("____________________________________________________________");
            }
        }

        System.out.println("____________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");

        scanner.close();
    }
}

class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void unmarkAsDone() {
        this.isDone = false;
    }
}