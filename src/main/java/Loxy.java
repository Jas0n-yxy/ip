import java.util.Scanner;
import java.util.ArrayList;

public class Loxy {
    private static final String SEPARATOR = "____________________________________________________________";
    private static ArrayList<String> taskList = new ArrayList<>();

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
            else {
                addTask(userInput);
            }
        }

        printGoodbyeMessage();
        scanner.close();
    }

    private static void printWelcomeLogo() {
        String logo =
                " | |     / __ \\\\ \\\\  // \\\\  //\n"
                        + " | |    | |  | |  \\\\//   \\\\// \n"
                        + " | |___ | |__| |  //\\\\    ||  \n"
                        + " |______|\\\\___/  //  \\\\   ||  \n";
        System.out.println("Hello from\n" + logo);
    }

    private static void printWelcomeMessage() {
        System.out.println(SEPARATOR);
        System.out.println(" Hello! I'm Loxy");
        System.out.println(" What can I do for you?");
        System.out.println(SEPARATOR);
    }

    private static void addTask(String task) {
        if (!task.isEmpty()) {
            taskList.add(task);
            System.out.println(SEPARATOR);
            System.out.println(" added: " + task);
            System.out.println(SEPARATOR);
        }
    }

    private static void printTaskList() {
        System.out.println(SEPARATOR);
        if (taskList.isEmpty()) {
            System.out.println(" No tasks added yet!");
        } else {
            for (int i = 0; i < taskList.size(); i++) {
                System.out.println(" " + (i + 1) + ". " + taskList.get(i));
            }
        }
        System.out.println(SEPARATOR);
    }

    private static void printGoodbyeMessage() {
        System.out.println(SEPARATOR);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(SEPARATOR);
    }
}