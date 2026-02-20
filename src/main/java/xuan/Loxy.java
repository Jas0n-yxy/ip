package xuan;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class LoxyException extends Exception {
    public LoxyException(String message) {
        super(message);
    }
}

public class Loxy {
    private static final String SEPARATOR = "____________________________________________________________";
    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static final String DATA_FOLDER = "./data";
    private static final String DATA_FILE = "./data/loxy.txt";

    public static void main(String[] args) {
        printWelcomeLogo();
        printWelcomeMessage();

        loadTasks();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String userInput = scanner.nextLine().trim();

            if ("bye".equalsIgnoreCase(userInput)) {
                break;
            } else if ("list".equalsIgnoreCase(userInput)) {
                printTaskList();
            } else if (userInput.startsWith("mark ")) {
                handleMarkTask(userInput, true);
                saveTasks();
            } else if (userInput.startsWith("unmark ")) {
                handleMarkTask(userInput, false);
                saveTasks();
            } else if (userInput.startsWith("todo ")) {
                try {
                    addTodoTask(userInput);
                    saveTasks();
                } catch (LoxyException e) {
                    printErrorMessage(e.getMessage());
                }
            } else if (userInput.startsWith("deadline ")) {
                addDeadlineTask(userInput);
                saveTasks();
            } else if (userInput.startsWith("event ")) {
                addEventTask(userInput);
                saveTasks();
            } else if (userInput.startsWith("delete ")) {
                try {
                    deleteTask(userInput);
                    saveTasks();
                } catch (LoxyException e) {
                    printErrorMessage(e.getMessage());
                }
            } else if (!userInput.isEmpty()) {
                try {
                    throw new LoxyException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                } catch (LoxyException e) {
                    printErrorMessage(e.getMessage());
                }
            } else {
                try {
                    throw new LoxyException("OOPS!!! Please enter a valid command.");
                } catch (LoxyException e) {
                    printErrorMessage(e.getMessage());
                }
            }
        }

        printGoodbyeMessage();
        scanner.close();
    }

    private static void loadTasks() {
        try {
            File folder = new File(DATA_FOLDER);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File file = new File(DATA_FILE);
            if (!file.exists()) {
                file.createNewFile();
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(" \\| ");
                if (parts.length < 3) {
                    System.out.println("警告：跳过损坏的任务行：" + line);
                    continue;
                }

                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                switch (type) {
                    case "T":
                        Todo todo = new Todo(description);
                        if (isDone) todo.markAsDone();
                        tasks.add(todo);
                        break;
                    case "D":
                        if (parts.length < 4) break;
                        String by = parts[3];
                        Deadline deadline = new Deadline(description, by);
                        if (isDone) deadline.markAsDone();
                        tasks.add(deadline);
                        break;
                    case "E":
                        if (parts.length < 5) break;
                        String from = parts[3];
                        String to = parts[4];
                        Event event = new Event(description, from, to);
                        if (isDone) event.markAsDone();
                        tasks.add(event);
                        break;
                    default:
                        System.out.println("警告：未知任务类型：" + type);
                }
            }
            br.close();
            System.out.println(SEPARATOR);
            System.out.println(" 成功加载 " + tasks.size() + " 个任务！");
            System.out.println(SEPARATOR);

        } catch (IOException e) {
            printErrorMessage("加载任务失败：" + e.getMessage());
        }
    }

    private static void saveTasks() {
        try {
            File folder = new File(DATA_FOLDER);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE));
            for (Task task : tasks) {
                String line = "";
                if (task instanceof Todo) {
                    line = String.format("T | %d | %s", task.isDone ? 1 : 0, task.description);
                } else if (task instanceof Deadline) {
                    Deadline d = (Deadline) task;
                    line = String.format("D | %d | %s | %s", d.isDone ? 1 : 0, d.description, d.by);
                } else if (task instanceof Event) {
                    Event e = (Event) task;
                    line = String.format("E | %d | %s | %s | %s", e.isDone ? 1 : 0, e.description, e.from, e.to);
                }
                bw.write(line);
                bw.newLine();
            }
            bw.close();

        } catch (IOException e) {
            printErrorMessage("保存任务失败：" + e.getMessage());
        }
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

    private static void addTodoTask(String userInput) throws LoxyException {
        try {
            String taskContent = userInput.substring(5).trim();
            if (taskContent.isEmpty()) {
                throw new LoxyException("Oops! The description of a todo cannot be empty.");
            }
            tasks.add(new Todo(taskContent));

            System.out.println(SEPARATOR);
            System.out.println(" Got it. I've added this task:");
            System.out.println("   " + tasks.get(tasks.size() - 1));
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            System.out.println(SEPARATOR);
        } catch (StringIndexOutOfBoundsException e) {
            throw new LoxyException("Oops! Please specify a description for the todo task.");
        }
    }

    private static void addDeadlineTask(String userInput) {
        try {
            String[] parts = userInput.substring(9).split(" /by ", 2);
            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                printErrorMessage("Oops! Please use format: deadline [content] /by [time]");
                return;
            }

            String description = parts[0].trim();
            String byTime = parts[1].trim();
            tasks.add(new Deadline(description, byTime));

            System.out.println(SEPARATOR);
            System.out.println(" Got it. I've added this task:");
            System.out.println("   " + tasks.get(tasks.size() - 1));
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            System.out.println(SEPARATOR);
        } catch (StringIndexOutOfBoundsException e) {
            printErrorMessage("Oops! Please specify a description and deadline time.");
        }
    }

    private static void addEventTask(String userInput) {
        try {
            String[] firstSplit = userInput.substring(6).split(" /from ", 2);
            if (firstSplit.length < 2 || firstSplit[0].trim().isEmpty()) {
                printErrorMessage("Oops! Please use format: event [content] /from [start] /to [end]");
                return;
            }

            String description = firstSplit[0].trim();
            String[] timeParts = firstSplit[1].split(" /to ", 2);
            if (timeParts.length < 2 || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
                printErrorMessage("Oops! Please specify both start and end time for event.");
                return;
            }

            String fromTime = timeParts[0].trim();
            String toTime = timeParts[1].trim();
            tasks.add(new Event(description, fromTime, toTime));

            System.out.println(SEPARATOR);
            System.out.println(" Got it. I've added this task:");
            System.out.println("   " + tasks.get(tasks.size() - 1));
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            System.out.println(SEPARATOR);
        } catch (StringIndexOutOfBoundsException e) {
            printErrorMessage("Oops! Please specify a description and time for the event.");
        }
    }

    private static void printTaskList() {
        System.out.println(SEPARATOR);
        if (tasks.isEmpty()) {
            System.out.println(" No tasks added yet!");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i));
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

            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                Task targetTask = tasks.get(taskIndex);
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
        } catch (NumberFormatException e) {
            printErrorMessage("Oops! Please enter a valid number after 'mark'/'unmark'.");
        }
    }

    private static void deleteTask(String userInput) throws LoxyException {
        try {
            String numberPart = userInput.substring(7).trim();
            int taskNumber = Integer.parseInt(numberPart);
            int taskIndex = taskNumber - 1;

            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new LoxyException("Oops! This task number does not exist.");
            }

            Task deletedTask = tasks.remove(taskIndex);
            System.out.println(SEPARATOR);
            System.out.println(" Noted. I've removed this task:");
            System.out.println("   " + deletedTask);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            System.out.println(SEPARATOR);

        } catch (StringIndexOutOfBoundsException e) {
            throw new LoxyException("Oops! Please specify a task number after 'delete'.");
        } catch (NumberFormatException e) {
            throw new LoxyException("Oops! Please enter a valid number after 'delete'.");
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
        protected String description;
        protected boolean isDone;

        public Task(String description) {
            this.description = description;
            this.isDone = false;
        }

        public void markAsDone() {
            this.isDone = true;
        }

        public void unmarkAsDone() {
            this.isDone = false;
        }

        protected String getStatusIcon() {
            return (isDone ? "X" : " ");
        }

        @Override
        public String toString() {
            return "[" + getStatusIcon() + "] " + description;
        }
    }

    private static class Todo extends Task {
        public Todo(String description) {
            super(description);
        }

        @Override
        public String toString() {
            return "[T]" + super.toString();
        }
    }

    private static class Deadline extends Task {
        protected String by;

        public Deadline(String description, String by) {
            super(description);
            this.by = by;
        }

        @Override
        public String toString() {
            return "[D]" + super.toString() + " (by: " + by + ")";
        }
    }

    private static class Event extends Task {
        protected String from;
        protected String to;

        public Event(String description, String from, String to) {
            super(description);
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
        }
    }
}