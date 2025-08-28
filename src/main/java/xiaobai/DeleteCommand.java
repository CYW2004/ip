package xiaobai;

public class DeleteCommand extends Command {
    private final int index;

    /**
     * Creates a DeleteCommand with the specified task index.
     *
     * @param index Index of the task to delete.
     */
    public DeleteCommand(int index) { this.index = index; }

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
        if (index < 1 || index > tasks.size()) throw new XiaoBaiException("(˙_˙) That task number is invalid.");
        Task t = tasks.remove(index);
        ui.printBoxed("Noted. I've removed this task:\n  " + t + "\nNow you have " + tasks.size() + " tasks in the list.");
        save(storage, tasks, ui);
    }
}
