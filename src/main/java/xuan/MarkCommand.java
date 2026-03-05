package xuan;

public class MarkCommand extends Command {
    private final int taskIndex;
    private final boolean isDone;

    public MarkCommand(int taskIndex, boolean isDone) {
        this.taskIndex = taskIndex;
        this.isDone = isDone;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LoxyException {
        tasks.markTask(taskIndex, isDone);
        ui.showTaskMarked(tasks.get(taskIndex), isDone);
        storage.save(tasks.getAllTasks());
    }
}