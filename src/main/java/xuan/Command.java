package xuan;

public abstract class Command {
    private boolean isExit = false;

    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws LoxyException;

    public boolean isExit() {
        return isExit;
    }

    protected void setExit(boolean exit) {
        isExit = exit;
    }
}