package xiaobai;

public class ListCommand extends Command {
    /**
     * Displays all tasks in the list.
     *
     * @param tasks Task list.
     * @param ui User interface.
     * @param storage Storage handler.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printBoxed(tasks.renderList());
    }
}
