package xiaobai;

public class AddDeadlineCommand extends Command {
    private final String desc, by;

    /**
     * Adds a Deadline task to the task list.
     *
     * @param desc Description of task.
     * @param by Deadline of task.
     */
    public AddDeadlineCommand(String desc, String by) {
        this.desc = desc; this.by = by;
    }

    /**
     * Creates a Deadline Task.
     * Saves the updated task list.
     *
     * @param tasks  Task list.
     * @param ui  User interface.
     * @param storage Storage handler.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws XiaoBaiException {
        if (desc == null || desc.isBlank() || by == null || by.isBlank()) {
            throw new XiaoBaiException("Invalid format. Use: deadline <description> /by <due>");
        }
        Task t = new Deadline(desc.trim(), by.trim());
        tasks.add(t);
        ui.printBoxed("Got it. I've added this task:\n  " + t + "\nNow you have " + tasks.size() + " tasks in the list.");
        save(storage, tasks, ui);
    }
}
