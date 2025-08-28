package xiaobai;

public class AddTodoCommand extends Command {
    private final String desc;
    public AddTodoCommand(String desc) { this.desc = desc; }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws XiaoBaiException {
        if (desc == null || desc.isBlank()) {
            throw new XiaoBaiException("The description of a todo cannot be empty.");
        }
        Task t = new Todo(desc.trim());
        tasks.add(t);
        ui.printBoxed("Got it. I've added this task:\n  " + t + "\nNow you have " + tasks.size() + " tasks in the list.");
        save(storage, tasks, ui);
    }
}
