package xiaobai;

public class AddEventCommand extends Command {
    private final String desc, start, end;
    public AddEventCommand(String desc, String start, String end) { this.desc = desc; this.start = start; this.end = end; }

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
