package xiaobai;

public class AddDeadlineCommand extends Command {
    private final String desc, by;
    public AddDeadlineCommand(String desc, String by) { this.desc = desc; this.by = by; }

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
