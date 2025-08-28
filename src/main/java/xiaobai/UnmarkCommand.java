package xiaobai;

public class UnmarkCommand extends Command {
    private final int index;

    /**
     * Creates an UnmarkCommand with the specified task index.
     *
     * @param index Index of the task to unmark.
     */
    public UnmarkCommand(int index) { this.index = index; }

    /**
     * Marks the task at the given index as not done.
     * Saves task the updated task list.
     * Throws XiaoBaiException if the index is out of bounds.
     *
     * @param tasks Task list.
     * @param ui User interface.
     * @param storage Storage handler.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws XiaoBaiException {
        if (index < 1 || index > tasks.size()) throw new XiaoBaiException("(˙_˙) That task number is invalid.");
        Task t = tasks.unmark(index);
        ui.printBoxed("OK, I've marked this task as not done yet:\n  " + t);
        save(storage, tasks, ui);
    }
}
