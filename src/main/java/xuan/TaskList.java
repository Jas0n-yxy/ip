package xuan;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;

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
            throw new LoxyException("This task number does not exist!");
        }
        return tasks.remove(index);
    }

    public void markTask(int index, boolean isDone) throws LoxyException {
        if (index < 0 || index >= tasks.size()) {
            throw new LoxyException("This task number does not exist!");
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
}