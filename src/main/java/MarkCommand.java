public class MarkCommand extends Command {
    private final int index;
    public MarkCommand(int index) { this.index = index; }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws XiaoBaiException {
        if (index < 1 || index > tasks.size()) throw new XiaoBaiException("(˙_˙) That task number is invalid.");
        Task t = tasks.mark(index);
        ui.printBoxed("Nice! I've marked this task as done:\n  " + t);
        save(storage, tasks, ui);
    }
}