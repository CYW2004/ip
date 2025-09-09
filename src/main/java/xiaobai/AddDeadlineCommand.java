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
        assert desc != null : "Description must not be null";
        assert by != null : "Deadline must not be null";
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
        assert tasks != null : "TaskList must not be null";
        assert ui != null : "Ui must not be null";
        assert storage != null : "Storage must not be null";
        if (desc == null || desc.isBlank() || by == null || by.isBlank()) {
            throw new XiaoBaiException("Invalid format. Use: deadline <description> /by <due>");
        }
        int oldSize = tasks.size();
        Task t = new Deadline(desc.trim(), by.trim());
        assert t != null : "Created task must not be null";
        tasks.add(t);
        assert tasks.size() == oldSize + 1 : "Task list size should increase after add";
        ui.printBoxed("Got it. I've added this task:\n  " + t + "\nNow you have " + tasks.size() + " tasks in the list.");
        save(storage, tasks, ui);
        assert tasks.size() > 0 : "After save, tasks should still be present";
    }
}
