package xiaobai;

public class MarkCommand extends Command {
    private final int index;
    /**
     * Marks a task in the task list as done.
     * Throws XiaoBaiException if the index is invalid.
     *
     * @param index Index of the task to mark as done.
     */
    public MarkCommand(int index) {
        assert index > 0 : "Index must be positive";
        this.index = index;
    }

    /**
     * Marks the task at the given index as done,
     * prints confirmation to the user, and saves the updated task list.
     * Throws XiaoBaiException if the index is out of bounds.
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
        Task t = tasks.mark(index);
        assert t != null : "Marked task must not be null";
        ui.printBoxed("Nice! I've marked this task as done:\n  " + t);
        save(storage, tasks, ui);
    }
}
