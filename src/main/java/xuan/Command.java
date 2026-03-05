package xuan;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws LoxyException;
    public boolean isExit() {
        return false;
    }
}