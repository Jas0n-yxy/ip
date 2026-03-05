package xuan;

import java.util.List;

public class FindByDateCommand extends Command {
    private final String dateStr;

    public FindByDateCommand(String dateStr) {
        this.dateStr = dateStr;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LoxyException {
        List<Task> foundTasks = tasks.findTasksByDate(dateStr);
        ui.showLine();
        if (foundTasks.isEmpty()) {
            System.out.println(" No tasks found on " + dateStr + "!");
        } else {
            System.out.println(" Here are the tasks on " + dateStr + ":");
            for (int i = 0; i < foundTasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + foundTasks.get(i));
            }
        }
        ui.showLine();
    }
}