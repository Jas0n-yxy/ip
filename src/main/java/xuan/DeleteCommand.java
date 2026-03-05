package xuan;

public class DeleteCommand extends Command {
    private final int taskIndex;

    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LoxyException {
        Task deletedTask = tasks.delete(taskIndex);
        ui.showTaskDeleted(deletedTask, tasks.size());
        storage.save(tasks.getAllTasks());
    }
}