package xuan;

public class Loxy {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Loxy(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        TaskList tempTasks;
        try {
            tempTasks = new TaskList(storage.load());
            ui.showLine();
            System.out.println(" Successfully loaded " + tempTasks.size() + " tasks!");
            ui.showLine();
        } catch (LoxyException e) {
            ui.showLoadingError();
            tempTasks = new TaskList();
        }
        tasks = tempTasks;
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command command = Parser.parse(fullCommand);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            } catch (LoxyException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Loxy("./data/loxy.txt").run();
    }
}