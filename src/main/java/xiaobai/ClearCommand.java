package xiaobai;

public class ClearCommand extends Command {

    /**
     * Clears all tasks from the task list,
     * prints confirmation to the user, and saving the updated task list.
     *
     * @param tasks Task list to be cleared.
     * @param ui User interface to display confirmation message.
     * @param storage Storage handler to save the cleared task list.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        assert tasks != null : "TaskList must not be null";
        assert ui != null : "Ui must not be null";
        assert storage != null : "Storage must not be null";
        tasks.asList().clear();
        assert tasks.size() == 0 : "Task list should be empty after clear";
        ui.printBoxed("Okay! I've cleared all tasks.\nNow you have 0 tasks in the list.");
        save(storage, tasks, ui);
        assert tasks.size() == 0 : "Tasks should remain empty after save";
    }
}
