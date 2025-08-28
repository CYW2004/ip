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
        if (desc == null || desc.isBlank() || start == null || start.isBlank() || end == null || end.isBlank()) {
            throw new XiaoBaiException("Invalid format. Use: event <description> /from <start> /to <end>");
        }
        Task t = new Event(desc.trim(), start.trim(), end.trim());
        tasks.add(t);
        ui.printBoxed("Got it. I've added this task:\n  " + t + "\nNow you have " + tasks.size() + " tasks in the list.");
        save(storage, tasks, ui);
    }
}
