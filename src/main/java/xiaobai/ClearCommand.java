package xiaobai;

public class ClearCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.asList().clear();
        ui.printBoxed("Okay! I've cleared all tasks.\nNow you have 0 tasks in the list.");
        save(storage, tasks, ui);
    }
}