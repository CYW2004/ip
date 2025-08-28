package xiaobai;

public class DeleteCommand extends Command {
    private final int index;
    public DeleteCommand(int index) { this.index = index; }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws XiaoBaiException {
        if (index < 1 || index > tasks.size()) throw new XiaoBaiException("(˙_˙) That task number is invalid.");
        Task t = tasks.remove(index);
        ui.printBoxed("Noted. I've removed this task:\n  " + t + "\nNow you have " + tasks.size() + " tasks in the list.");
        save(storage, tasks, ui);
    }
}
