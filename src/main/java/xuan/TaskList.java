package xuan;

/**
 * Contains the task list and operations on it.
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int index) throws LoxyException {
        if (index < 0 || index >= tasks.size()) {
            throw new LoxyException("Task number does not exist!");
        }
        return tasks.remove(index);
    }

    public void markTask(int index, boolean isDone) throws LoxyException {
        if (index < 0 || index >= tasks.size()) {
            throw new LoxyException("Task number does not exist!");
        }
        Task task = tasks.get(index);
        if (isDone) {
            task.markAsDone();
        } else {
            task.unmarkAsDone();
        }
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public List<Task> findTasksByDate(String dateStr) throws LoxyException {
        List<Task> result = new ArrayList<>();
        LocalDate targetDate;
        try {
            targetDate = LocalDate.parse(dateStr, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new LoxyException("Invalid date format! Use yyyy-MM-dd");
        }

        for (Task task : tasks) {
            if (task instanceof Deadline deadline && deadline.getByDate().equals(targetDate)) {
                result.add(task);
            } else if (task instanceof Event event && !event.getFromDate().isAfter(targetDate) && !event.getToDate().isBefore(targetDate)) {
                result.add(task);
            }
        }
        return result;
    }

    public List<Task> findTasksByKeyword(String keyword) {
        List<Task> result = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(lowerKeyword)) {
                result.add(task);
            }
        }
        return result;
    }
}