package xiaobai;

public class DeleteCommand extends Command {
    private final int index;

    /**
     * Creates a DeleteCommand with the specified task index.
     *
     * @param index Index of the task to delete.
     */
    public DeleteCommand(int index) {
        assert index > 0 : "Index must be positive";
        this.index = index;
    }

    /**
     * Deletes the task at the given index from the task list,
     * prints confirmation to the user, and saves the updated task list.
     *
     * @param tasks Task list.
     * @param ui User interface.
     * @param storage Storage handler.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws XiaoBaiException {
        assert tasks != null : "TaskList must not be null";
        assert ui != null : "Ui must not be null";
        assert storage != null : "Storage must not be null";
        if (index < 1 || index > tasks.size()) throw new XiaoBaiException("(˙_˙) That task number is invalid.");
        int oldSize = tasks.size();
        Task t = tasks.remove(index);
        assert t != null : "Removed task must not be null";
        assert tasks.size() == oldSize - 1 : "Task list size should decrease after delete";
        ui.printBoxed("Noted. I've removed this task:\n  " + t + "\nNow you have " + tasks.size() + " tasks in the list.");
        save(storage, tasks, ui);
        assert tasks.size() >= 0 : "Task list size must not be negative";
    }
}
