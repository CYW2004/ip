public class UnmarkCommand extends Command {
    private final int index;
    public UnmarkCommand(int index) { this.index = index; }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws XiaoBaiException {
        if (index < 1 || index > tasks.size()) throw new XiaoBaiException("(˙_˙) That task number is invalid.");
        Task t = tasks.unmark(index);
        ui.printBoxed("OK, I've marked this task as not done yet:\n  " + t);
        save(storage, tasks, ui);
    }
}