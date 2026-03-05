package xuan;

import java.util.List;

public class FindByKeywordCommand extends Command {
    private final String keyword;

    public FindByKeywordCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        List<Task> foundTasks = tasks.findTasksByKeyword(keyword);
        ui.showLine();
        if (foundTasks.isEmpty()) {
            System.out.println(" No tasks found for: '" + keyword + "'");
        } else {
            System.out.println(" Matching tasks:");
            for (int i = 0; i < foundTasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + foundTasks.get(i));
            }
        }
        ui.showLine();
    }
}