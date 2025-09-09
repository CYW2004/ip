package xiaobai;

public class UnmarkCommand extends Command {
    private final int index;

    /**
     * Creates an UnmarkCommand with the specified task index.
     *
     * @param index Index of the task to unmark as done.
     */
    public UnmarkCommand(int index) {
        assert index > 0 : "Index must be positive";
        this.index = index;
    }

    /**
     * Unmarks the task at the given index as not done,
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
        Task t = tasks.unmark(index);
        assert t != null : "Unmarked task must not be null";
        ui.printBoxed("OK, I've marked this task as not done yet:\n  " + t);
        save(storage, tasks, ui);
    }
}
