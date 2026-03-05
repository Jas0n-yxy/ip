package xuan;

/**
 * Base class for all commands.
 */
public abstract class Command {
    public abstract void execute(TaskList t, Ui u, Storage s) throws LoxyException;
    public boolean isExit() { return false; }
}