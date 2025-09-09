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
        assert tasks != null : "TaskList must not be null";
        assert ui != null : "Ui must not be null";
        assert storage != null : "Storage must not be null";
        String rendered = tasks.renderList();
        assert rendered != null : "Rendered task list must not be null";
        ui.printBoxed(rendered);
    }
}
