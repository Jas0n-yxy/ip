import java.util.Scanner;
import java.util.ArrayList;

public class Loxy {
    // Unified separator constant to ensure consistent output format
    private static final String SEPARATOR = "____________________________________________________________";
    // Task list to store all tasks
    private static ArrayList<Task> taskList = new ArrayList<>();

    public static void main(String[] args) {
        printWelcomeLogo();
        printWelcomeMessage();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String userInput = scanner.nextLine().trim(); // Remove leading/trailing spaces to improve fault tolerance

            // Exit command
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
            // Delete task
            else if (userInput.startsWith("delete ")) {
                handleDeleteTask(userInput);
            }
            // Add Todo task
            else if (userInput.startsWith("todo ")) {
                addTodoTask(userInput);
            }
            // Add Deadline task
            else if (userInput.startsWith("deadline ")) {
                addDeadlineTask(userInput);
            }
            // Add Event task
            else if (userInput.startsWith("event ")) {
                addEventTask(userInput);
            }
            // Invalid command
            else {
                printErrorMessage("Oops! I don't understand this command.\nValid commands: todo, deadline, event, list, mark, unmark, delete, bye");
            }
        }

        printGoodbyeMessage();
        scanner.close();
    }

    // Print welcome logo
    private static void printWelcomeLogo() {
        String logo =
                " | |     / __ \\\\ \\\\  // \\\\  //\n"
                        + " | |    | |  | |  \\\\//   \\\\// \n"
                        + " | |___ | |__| |  //\\\\    ||  \n"
                        + " |______|\\\\___/  //  \\\\   ||  \n";
        System.out.println("Hello from\n" + logo);
    }

    // Print welcome message
    private static void printWelcomeMessage() {
        System.out.println(SEPARATOR);
        System.out.println(" Hello! I'm Loxy");
        System.out.println(" What can I do for you?");
        System.out.println(SEPARATOR);
    }

    // Print goodbye message
    private static void printGoodbyeMessage() {
        System.out.println(SEPARATOR);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(SEPARATOR);
    }

    // Print all tasks in the list
    private static void printTaskList() {
        System.out.println(SEPARATOR);
        if (taskList.isEmpty()) {
            System.out.println(" Here are the tasks in your list:");
            System.out.println(" No tasks yet!");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < taskList.size(); i++) {
                Task task = taskList.get(i);
                System.out.println(" " + (i + 1) + "." + task);
            }
        }
        System.out.println(SEPARATOR);
    }

    // Handle marking task as done or not done
    // Parameters: userInput - the input string from user; isMarkDone - true for marking done, false for not done
    private static void handleMarkTask(String userInput, boolean isMarkDone) {
        try {
            String cmdPrefix = isMarkDone ? "mark " : "unmark ";
            String numberPart = userInput.substring(cmdPrefix.length());
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
        } catch (NumberFormatException e) {
            printErrorMessage("Oops! Please enter a valid number after 'mark'/'unmark'.");
        } catch (StringIndexOutOfBoundsException e) {
            printErrorMessage("Oops! Please specify a task number after 'mark'/'unmark'.");
        }
    }

    // Handle deleting a task
    private static void handleDeleteTask(String userInput) {
        try {
            String numberPart = userInput.substring(7);
            int taskNumber = Integer.parseInt(numberPart);
            int taskIndex = taskNumber - 1;

            if (taskIndex >= 0 && taskIndex < taskList.size()) {
                Task deletedTask = taskList.remove(taskIndex);
                System.out.println(SEPARATOR);
                System.out.println(" Noted! I've removed this task:");
                System.out.println("   " + deletedTask);
                System.out.println(" Now you have " + taskList.size() + " tasks in the list.");
                System.out.println(SEPARATOR);
            } else {
                printErrorMessage("Oops! This task number does not exist.");
            }
        } catch (NumberFormatException e) {
            printErrorMessage("Oops! Please enter a valid number after 'delete'.");
        } catch (StringIndexOutOfBoundsException e) {
            printErrorMessage("Oops! Please specify a task number after 'delete'.");
        }
    }

    // Add a Todo task
    private static void addTodoTask(String userInput) {
        String description = userInput.substring(5).trim();
        if (description.isEmpty()) {
            printErrorMessage("Oops! The description of a todo cannot be empty.");
            return;
        }
        Todo newTodo = new Todo(description);
        taskList.add(newTodo);
        printAddSuccessMessage(newTodo);
    }

    // Add a Deadline task
    private static void addDeadlineTask(String userInput) {
        String deadlineInput = userInput.substring(9).trim();
        // Check if the input contains /by separator
        if (!deadlineInput.contains("/by")) {
            printErrorMessage("Oops! Deadline tasks need '/by' to specify due time (e.g., deadline return book /by Sunday).");
            return;
        }
        // Split description and due time (split only at the first occurrence of /by to avoid /by in time string)
        String[] parts = deadlineInput.split("/by", 2);
        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty()) {
            printErrorMessage("Oops! The description of a deadline cannot be empty.");
            return;
        }
        // Due time can be empty (as per example, e.g., input /by with no content)
        Deadline newDeadline = new Deadline(description, by);
        taskList.add(newDeadline);
        printAddSuccessMessage(newDeadline);
    }

    // Add an Event task
    private static void addEventTask(String userInput) {
        String eventInput = userInput.substring(6).trim();
        // Check if the input contains /from and /to separators
        if (!eventInput.contains("/from") || !eventInput.contains("/to")) {
            printErrorMessage("Oops! Event tasks need '/from' and '/to' (e.g., event meeting /from 2pm /to 4pm).");
            return;
        }
        // Split description and time part by /from first
        String[] fromParts = eventInput.split("/from", 2);
        String description = fromParts[0].trim();
        String timePart = fromParts[1].trim();

        // Split start and end time by /to
        if (!timePart.contains("/to")) {
            printErrorMessage("Oops! Event tasks need '/to' to specify end time (e.g., event meeting /from 2pm /to 4pm).");
            return;
        }
        String[] toParts = timePart.split("/to", 2);
        String from = toParts[0].trim();
        String to = toParts[1].trim();

        if (description.isEmpty()) {
            printErrorMessage("Oops! The description of an event cannot be empty.");
            return;
        }
        // Start and end time can be empty
        Event newEvent = new Event(description, from, to);
        taskList.add(newEvent);
        printAddSuccessMessage(newEvent);
    }

    // Print success message after adding a task
    private static void printAddSuccessMessage(Task task) {
        System.out.println(SEPARATOR);
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskList.size() + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    // Unified method to print error messages
    private static void printErrorMessage(String message) {
        System.out.println(SEPARATOR);
        System.out.println(" " + message);
        System.out.println(SEPARATOR);
    }
}

// Base Task class (parent class for all task types)
class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    // Get status icon (X = done, space = not done)
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    // Mark task as done
    public void markAsDone() {
        this.isDone = true;
    }

    // Mark task as not done
    public void unmarkAsDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}

// Todo task class (no time attributes)
class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T][" + getStatusIcon() + "] " + description;
    }
}

// Deadline task class (with due time)
class Deadline extends Task {
    protected String by; // Due time (string type, no need to parse to date/time)

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D][" + getStatusIcon() + "] " + description + " (by: " + by + ")";
    }
}

// Event task class (with start and end time)
class Event extends Task {
    protected String from; // Start time
    protected String to;   // End time

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E][" + getStatusIcon() + "] " + description + " (from: " + from + " to: " + to + ")";
    }
}