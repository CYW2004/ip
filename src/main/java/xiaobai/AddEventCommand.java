package xiaobai;

public class AddEventCommand extends Command {
    private final String desc, start, end;

    /**
     * Creates an AddEventCommand with the given description, start, and end time.
     *
     * @param desc Description of task.
     * @param start Start time of event.
     * @param end End time of event.
     */
    public AddEventCommand(String desc, String start, String end) {
        assert desc != null : "Description must not be null";
        assert start != null : "Start time must not be null";
        assert end != null : "End time must not be null";
        this.desc = desc; this.start = start; this.end = end;
    }

    /**
     * Creates an Event task.
     * Saves the updated task list.
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
        if (desc == null || desc.isBlank() || start == null || start.isBlank() || end == null || end.isBlank()) {
            throw new XiaoBaiException("Invalid format. Use: event <description> /from <start> /to <end>");
        }
        int oldSize = tasks.size();
        Task t = new Event(desc.trim(), start.trim(), end.trim());
        assert t != null : "Created task must not be null";
        tasks.add(t);
        assert tasks.size() == oldSize + 1 : "Task list size should increase after add";
        ui.printBoxed("Got it. I've added this task:\n  " + t + "\nNow you have " + tasks.size() + " tasks in the list.");
        save(storage, tasks, ui);
        assert tasks.size() > 0 : "After save, tasks should still be present";
    }
}
