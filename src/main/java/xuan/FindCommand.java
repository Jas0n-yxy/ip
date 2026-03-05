package xuan;

import java.util.List;

public class FindCommand extends Command {
    private final String dateStr;

    public FindCommand(String dateStr) {
        this.dateStr = dateStr;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LoxyException {
        List<Task> foundTasks = tasks.findTasksByDate(dateStr);
        ui.showFoundTasks(foundTasks, dateStr);
    }
}