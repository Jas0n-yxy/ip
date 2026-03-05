package xuan;

/**
 * Main entry point of the application.
 */
public class Loxy {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Loxy(String path) {
        ui = new Ui();
        storage = new Storage(path);
        TaskList temp;
        try {
            temp = new TaskList(storage.load());
        } catch (LoxyException e) {
            ui.showLoadingError();
            temp = new TaskList();
        }
        tasks = temp;
    }

    public void run() {
        ui.showWelcome();
        boolean exit = false;
        while (!exit) {
            try {
                String c = ui.readCommand();
                Command cmd = Parser.parse(c);
                cmd.execute(tasks, ui, storage);
                exit = cmd.isExit();
            } catch (LoxyException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Loxy("./data/loxy.txt").run();
    }
}